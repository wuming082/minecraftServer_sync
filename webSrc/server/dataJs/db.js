//db.js

const sqlite3 = require('sqlite3').verbose();
const path = require('path');

const dbPath = path.resolve(__dirname, 'sqlite_codedockerUI');
const db = new sqlite3.Database(dbPath);

//打开数据库
db.serialize(() => {
    //用户基本信息
    db.run(`
        CREATE TABLE IF NOT EXISTS users (
          id INTEGER PRIMARY KEY AUTOINCREMENT,
          username TEXT UNIQUE NOT NULL,
          password TEXT NOT NULL,
          email TEXT NOT NULL
        )
    `);
    //创建用户启动容器ID
    db.run(`
        CREATE TABLE IF NOT EXISTS docker_containers (  
          id INTEGER PRIMARY KEY AUTOINCREMENT,
          container_id TEXT NOT NULL,
          username TEXT NOT NULL,
          container_enter_password TEXT NOT NULL, 
          project_contaienr TEXT NOT NULL,
          FOREIGN KEY (username) REFERENCES users(username)   
        )
    `);
});

module.exports = db;
    
