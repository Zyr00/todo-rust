#[macro_use]
extern crate log;

use actix_web::{ App, HttpServer, web, HttpResponse };
use listenfd::ListenFd;

mod config;
mod error;
mod todos;
use config::{ Config };
use error::ApiError;

pub async fn error() -> Result<HttpResponse, ApiError> {
    Err(ApiError::error(404, "Nothing Here!"))
}

#[actix_rt::main]
async fn main() -> std::io::Result<()> {
    env_logger::init();

    let config = Config::from_env();

    let mut listenfd = ListenFd::from_env();
    let mut server = HttpServer::new(||
        App::new()
            .configure(todos::init_routes)
            .route("/*", web::get().to(error))
    );

    server = match listenfd.take_tcp_listener(0)? {
        Some(listener) => server.listen(listener)?,
        None => {
            server.bind(format!("{}:{}",
                        config.server.host, config.server.port))?
        }
    };

    info!("Starting server http://{}:{}",
          config.server.host, config.server.port);
    server.run().await
}
