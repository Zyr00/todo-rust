#[macro_use]
extern crate log;

use actix_web::{ App, HttpServer, web, http::StatusCode, http::Method };
use actix_web::middleware::errhandlers::ErrorHandlers;
use actix_web::middleware::{ Logger, NormalizePath };
use tera::{ Tera };

mod config;
mod error;
mod todos;
use config::{ Config };

#[actix_rt::main]
async fn main() -> std::io::Result<()> {
    let config = Config::from_env();
    env_logger::init();

    let url = format!("{}:{}", config.server.host, config.server.port);
    info!("Starting server http://{}", url);

    let server = HttpServer::new(|| {
        let tera = Tera::new("templates/**/*").unwrap();
        App::new()
            .wrap(Logger::default())
            .wrap(Logger::new("%a %{User-Agent}i"))
            .wrap(NormalizePath::default())
            .wrap(
                ErrorHandlers::new()
                    .handler(StatusCode::NOT_FOUND, error::handle_error)
            )
            .data(tera)
            .service(web::scope("/todo").configure(todos::view_routes))
            .service(web::scope("/api/todo").configure(todos::api_routes))
            .default_service(web::route().method(Method::GET))
    })
    .bind(&url)?;

    server.run().await
}
