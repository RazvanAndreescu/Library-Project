package com.sda.practical.test1;

import com.sda.practical.utils.LoggerUtils;

public class PostgreSQLConnection implements IDBConnection{
    @Override
    public void createConnection() {
        LoggerUtils.print("PostgreSQLConnection");
    }
}
