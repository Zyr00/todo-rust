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

impl Todo {
    pub fn find_all() -> Result<Vec<Self>, ApiError> {
        let mut conn = db::connection()?;
        let statement = conn.prepare("SELECT * FROM todo")?;

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
            return Err(ApiError::error(404, &message))
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
}
