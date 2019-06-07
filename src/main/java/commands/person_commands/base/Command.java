package commands.person_commands.base;

import java.sql.SQLException;

@FunctionalInterface
public interface Command {
    String execute() throws SQLException;
}
