package FileBasedDatabase;

abstract class SQLStatement {
    protected String statementType;
    protected String tableName;

    public abstract void executeStatement();
}

class SelectStatement extends SQLStatement {
    private String columns;
    private String conditions;

    public SelectStatement(String tableName, String columns, String conditions) {
        this.statementType = "SELECT";
        this.tableName = tableName;
        this.columns = columns;
        this.conditions = conditions;
    }

    @Override
    public void executeStatement() {
        System.out.println("SELECT executed:\n  Name:" + this.tableName + "\n  Columns:" + this.columns);
        if(this.conditions != null) {
            System.out.print("  Conditions:" + this.conditions);
        }
        System.out.println("\n");
    }
}

class InsertStatement extends SQLStatement {
    private String values;

    public InsertStatement(String tableName, String values) {
        this.statementType = "INSERT";
        this.tableName = tableName;
        this.values = values;
    }

    @Override
    public void executeStatement() {}
    
}

class UpdateStatement extends SQLStatement {
    private String updates;
    private String conditions;

    public UpdateStatement(String tableName, String updates, String conditions) {
        this.statementType = "UPDATE";
        this.tableName = tableName;
        this.updates = updates;
        this.conditions = conditions;
    }

    @Override
    public void executeStatement() {}
}

class DeleteStatement extends SQLStatement {
    private String conditions;

    public DeleteStatement(String tableName, String conditions) {
        this.statementType = "DELETE";
        this.tableName = tableName;
        this.conditions = conditions;
    }

    @Override
    public void executeStatement() {}
}