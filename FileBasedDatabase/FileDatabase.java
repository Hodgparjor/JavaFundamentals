package FileBasedDatabase;
import java.util.*;

public class FileDatabase {
    public static void main(String[] args) {
        Scanner querySQL = new Scanner(System.in);
        System.out.print("\nEnter SQL statement: ");
        String insertedLine = querySQL.nextLine();
        insertedLine.trim();

        while (insertedLine != null && !insertedLine.isEmpty()) {
            try {
                StatementMatcher matcherSQL = new StatementMatcher(insertedLine);
                ArrayList<String> parameters = matcherSQL.getStatementParameters();
                String tableName = parameters.get(0);
                if (matcherSQL.getStatementType() == "SELECT") {
                    String columns = parameters.get(1);
                    String conditions = parameters.get(2);
                    SelectStatement select = new SelectStatement(tableName, columns, conditions);
                    select.executeStatement();
                } else if (matcherSQL.getStatementType() == "INSERT") {
                    String values = parameters.get(1);
                    InsertStatement insert = new InsertStatement(tableName, values);
                    insert.executeStatement();
                } else if (matcherSQL.getStatementType() == "UPDATE") {
                    String updates = parameters.get(1);
                    String conditions = parameters.get(2);
                    UpdateStatement update = new UpdateStatement(tableName, updates, conditions);
                    update.executeStatement();
                } else if (matcherSQL.getStatementType() == "DELETE") {
                    String conditions = parameters.get(1);
                    DeleteStatement delete = new DeleteStatement(tableName, conditions);
                    delete.executeStatement();
                } else if (matcherSQL.getStatementType() == "CREATE") {
                    String tableHeaders = parameters.get(1);
                    CreateStatement create = new CreateStatement(tableName, tableHeaders);
                    create.executeStatement();
                } else if (matcherSQL.getStatementType() == "DROP") {
                    DropStatement drop = new DropStatement(tableName);
                    drop.executeStatement();
                }
            } catch (InvalidSQLStatementException e) {
                System.err.println(e.getMessage());
            }

            System.out.print("\nEnter SQL statement: ");
            insertedLine = querySQL.nextLine();
        }
        querySQL.close();
    }
}
