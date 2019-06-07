package model.db.dao;

import model.extra.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class InvoiceDao extends AbstractDao<Invoice, Integer> {

    public InvoiceDao(Connection connection) { super(connection); }

    @Override
    public List<Invoice> getAll() throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM invoices");
        List<Invoice> result = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_application = resultSet.getInt(2);
                int id_master = resultSet.getInt(3);
                int price = resultSet.getInt(4);
                result.add(new Invoice(id, id_application, id_master, price));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }


    public List<Invoice> getFinished() throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM invoices WHERE is_done = 1");
        List<Invoice> result = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_application = resultSet.getInt(2);
                int id_master = resultSet.getInt(3);
                int price = resultSet.getInt(4);
                result.add(new Invoice(id, id_application, id_master, price));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }

    public List<Invoice> getNotDoneForMaster(Integer masterId) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM invoices WHERE is_done = 0 AND id_master = ?");
        List<Invoice> result = new ArrayList<>();

        try {
            preparedStatement.setInt(1, masterId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_application = resultSet.getInt(2);
                int id_master = resultSet.getInt(3);
                int price = resultSet.getInt(4);
                result.add(new Invoice(id, id_application, id_master, price));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }

    public List<Invoice> getFinishedForCustomer(Integer personId) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM invoices WHERE is_done = 1 AND (id_application in (SELECT id FROM applications WHERE id_person_info = ?))");
        List<Invoice> result = new ArrayList<>();

        try {
            preparedStatement.setInt(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_application = resultSet.getInt(2);
                int id_master = resultSet.getInt(3);
                int price = resultSet.getInt(4);
                result.add(new Invoice(id, id_application, id_master, price));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }

//    public List<Invoice> getFinished() throws SQLException {
//        var preparedStatement = getPrepareStatement("SELECT * FROM invoices WHERE is_done = 1");
//        var result = new ArrayList<Invoice>();
//
//        try {
//            var resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                var id = resultSet.getInt(1);
//                var id_application = resultSet.getInt(2);
//                var id_master = resultSet.getInt(3);
//                var price = resultSet.getInt(4);
//                result.add(new Invoice(id, id_application, id_master, price));
//            }
//        } finally {
//            closePrepareStatement(preparedStatement);
//        }
//
//        return result;
//    }

    @Override
    public Invoice getEntityById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM invoices WHERE id = ?");
        Invoice invoice = null;

        try {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id_application = resultSet.getInt(2);
                int id_master = resultSet.getInt(3);
                int price = resultSet.getInt(4);
                invoice = new Invoice(id, id_application, id_master, price);
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return invoice;
    }

    @Override
    public boolean update(Integer id, Invoice entity) throws SQLException {
        return handleQuery(
                "UPDATE invoices SET id_application = ?, id_master = ?, price = ?, is_done = ? WHERE id = ?" ,
                entity.idApplication, entity.idMaster, entity.price, entity.isDone, id
        );
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return handleQuery(
                "DELETE FROM invoices WHERE id = ?",
                id
        );
    }

    @Override
    public boolean create(Invoice entity) throws SQLException {
        return handleQuery(
                "INSERT INTO invoices (id_application, id_master, price, is_done) VALUES (?, ?, ?, ?)",
                entity.idApplication, entity.idMaster, entity.price, entity.isDone
        );
    }
}
