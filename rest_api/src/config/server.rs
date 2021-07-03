use dotenv::dotenv;
use std::env;

pub struct ServerConfig {
    pub host: String,
    pub port: String
}

pub struct DBConfig {
    pub host: String,
    pub username: String,
    pub password: String
}

pub struct Config {
    pub server: ServerConfig,
    pub db: DBConfig
}

impl Config {
    pub fn from_env() -> Config {
        dotenv().ok();

        let server = ServerConfig {
            host: env::var("HOST").expect("Host not set"),
            port: env::var("PORT").expect("Port not set")
        };

        let db = DBConfig {
            host: env::var("HOST_DB").expect("Host not set"),
            username: env::var("USER_DB").expect("User not set"),
            password: env::var("PASS_DB").expect("Pass not set")
        };

        let config = Config {
            server,
            db
        };

        return config;
    }
}
