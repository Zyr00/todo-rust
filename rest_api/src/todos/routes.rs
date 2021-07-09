use crate::error::ApiError;
use crate::todos::{ Todo, TodoInit };
use actix_web::{ delete, get, post, put, web, HttpResponse, Responder };
use tera::{ Tera, Context };
use serde_json::json;

#[get("/")]
async fn find_all() -> Result<HttpResponse, ApiError> {
    let todos = Todo::find_all()?;
    Ok(HttpResponse::Ok().json(json!( { "todos": todos } )))
}

#[get("/id/{id}/")]
async fn find(id: web::Path<i32>) -> Result<HttpResponse, ApiError> {
    let todo = Todo::find(id.into_inner())?;
    Ok(HttpResponse::Ok().json(json!( { "todo": todo } )))
}

#[get("/{val}/")]
async fn find_by(val: web::Path<String>) -> Result<HttpResponse, ApiError> {
    let todos = Todo::find_by(&val.into_inner())?;
    Ok(HttpResponse::Ok().json(json!( { "todos": todos } )))
}

#[post("/")]
async fn create(todo: web::Json<TodoInit>) -> Result<HttpResponse, ApiError> {
    let todo = Todo::create(todo.into_inner())?;
    Ok(HttpResponse::Ok().json(json!( { "message": "Created", "todo": todo } )))
}

#[delete("/{id}/")]
async fn delete(id: web::Path<i32>) -> Result<HttpResponse, ApiError> {
    let v = id.into_inner();
    Todo::delete(v)?;
    let message = format!("{} Deleted", v);
    Ok(HttpResponse::Ok().json(json!( { "message": message } )))
}

#[put("/{id}/")]
async fn update(id: web::Path<i32>, todo: web::Json<TodoInit>) -> Result<HttpResponse, ApiError> {
    let id = id.into_inner();
    Todo::update(id, todo.into_inner())?;
    let message = format!("Todo with id {} updated", id);
    Ok(HttpResponse::Ok().json(json!({ "message": message })))
}

#[get("/")]
async fn view_list(tera: web::Data<Tera>) -> Result<impl Responder, ApiError> {
    let todos = Todo::find_all()?;
    let mut data = Context::new();
    data.insert("title", "My todos");
    data.insert("todos", &todos);
    let rendered = tera.render("todos/todos.html", &data).unwrap();
    Ok(HttpResponse::Ok().body(rendered))
}

#[get("/{id}/")]
async fn view_edit(id: web::Path<i32>, tera: web::Data<Tera>) -> Result<impl Responder, ApiError> {
    let id = id.into_inner();
    let todo = Todo::find(id)?;
    let mut data = Context::new();
    data.insert("title", &format!("Edit todos {}", id));
    data.insert("todo", &todo);
    let rendered = tera.render("todos/edit.html", &data).unwrap();
    Ok(HttpResponse::Ok().body(rendered))
}

#[get("/add/")]
async fn view_add(tera: web::Data<Tera>) -> Result<impl Responder, ApiError> {
    let mut data = Context::new();
    data.insert("title", "Add new todo");
    let rendered = tera.render("todos/add.html", &data).unwrap();
    Ok(HttpResponse::Ok().body(rendered))
}

pub fn api_routes(cfg: &mut web::ServiceConfig) {
    cfg.service(find_all);
    cfg.service(find);
    cfg.service(find_by);
    cfg.service(create);
    cfg.service(delete);
    cfg.service(update);
}

pub fn view_routes(cfg: &mut web::ServiceConfig) {
    cfg.service(view_add);
    cfg.service(view_list);
    cfg.service(view_edit);
}
