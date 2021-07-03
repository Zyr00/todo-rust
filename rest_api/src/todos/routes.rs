use crate::error::ApiError;
use crate::todos::Todo;
use actix_web::{delete, get, post, put, web, HttpResponse};
use serde_json::json;

#[get("/todo")]
async fn find_all() -> Result<HttpResponse, ApiError> {
    let todos = Todo::find_all()?;
    Ok(HttpResponse::Ok().json(json!( { "todos": todos } )))
}

#[get("/todo/{id}")]
async fn find(id: web::Path<i32>) -> Result<HttpResponse, ApiError> {
    let todo = Todo::find(id.into_inner())?;
    Ok(HttpResponse::Ok().json(json!( { "todo": todo } )))
}

pub fn init_routes(cfg: &mut web::ServiceConfig) {
    cfg.service(find_all);
    cfg.service(find);
}
