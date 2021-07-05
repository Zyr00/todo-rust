use crate::error::ApiError;
use crate::config::db;
use serde::{ Serialize, Deserialize };

#[derive(Serialize, Deserialize)]
pub struct Todo {
    id: i32,
    title: String,
    desc: String,
    done: bool
}

#[derive(Serialize, Deserialize)]
pub struct TodoInit {
    title: String,
    desc: String,
    done: bool
}

impl Todo {
    pub fn find_all() -> Result<Vec<Self>, ApiError> {
        let mut conn = db::connection()?;
        let statement = conn.prepare("SELECT * FROM todo ORDER BY id")?;

        let mut todos: Vec<Self> = vec![];
        for row in conn.query(&statement, &[])? {
            let todo = Todo {
                id: row.get(0),
                title: row.get(1),
                desc: row.get(2),
                done: row.get(3)
            };
            todos.push(todo);
        }
        Ok(todos)
    }

    pub fn find(id: i32) -> Result<Self, ApiError> {
        let mut conn = db::connection()?;
        let statement = conn.prepare("SELECT * FROM todo WHERE id = $1")?;
        let rows = conn.query(&statement, &[&id])?;

        if rows.len() <= 0 {
            let message: String = format!("Todo {} does not exit.", id);
            return Err(ApiError::error(400, &message))
        };

        let row = &rows[0];
        let todo: Self = Todo {
            id: row.get(0),
            title: row.get(1),
            desc: row.get(2),
            done: row.get(3),
        };
        Ok(todo)
    }

    pub fn create(todo: TodoInit) -> Result<Self, ApiError> {
        let mut conn = db::connection()?;
        let statement = conn.prepare("INSERT INTO todo (title, vdesc, done) VALUES ($1, $2, $3)")?;
        conn.execute(&statement, &[&todo.title, &todo.desc, &todo.done])?;

        let statement = conn.prepare("SELECT * FROM todo ORDER BY id DESC LIMIT 1")?;
        let rows = conn.query(&statement, &[])?;

        if rows.len() <= 0 {
            let message: String = format!("Todo does not exit.");
            return Err(ApiError::error(400, &message))
        };

        let row = &rows[0];
        let todo: Self = Todo {
            id: row.get(0),
            title: row.get(1),
            desc: row.get(2),
            done: row.get(3),
        };
        Ok(todo)
    }

    pub fn delete(id: i32) -> Result<(), ApiError> {
        let mut conn = db::connection()?;
        let statement = conn.prepare("SELECT * FROM todo WHERE id = $1")?;
        let rows = conn.query(&statement, &[&id])?;

        if rows.len() <= 0 {
            let message: String = format!("Todo with id {} does not exit.", id);
            return Err(ApiError::error(400, &message))
        };

        let statement = conn.prepare("DELETE FROM todo WHERE id = $1")?;
        conn.execute(&statement, &[&id])?;
        Ok(())
    }

    pub fn update(id: i32, todo: TodoInit) -> Result<(), ApiError> {
        let mut conn = db::connection()?;
        let statement = conn.prepare("SELECT * FROM todo WHERE id = $1")?;
        let rows = conn.query(&statement, &[&id])?;

        if rows.len() <= 0 {
            let message: String = format!("Todo with id {} does not exit.", id);
            return Err(ApiError::error(400, &message))
        };

        let statement = conn.prepare("UPDATE todo SET title = $1, vdesc = $2, done = $3 WHERE id = $4")?;
        conn.execute(&statement, &[&todo.title, &todo.desc, &todo.done, &id])?;
        Ok(())
    }
}
