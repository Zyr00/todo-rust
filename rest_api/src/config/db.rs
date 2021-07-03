use postgres::{ Client, NoTls };

use crate::error::ApiError;
use crate::config::Config;

fn get_creds() -> String {
    let c = Config::from_env().db;
    format!("host={} user={} password={}", c.host, c.username, c.password)
}

pub fn connection() -> Result<Client, ApiError> {
    let client = match Client::connect(&get_creds(), NoTls) {
        Ok(c) => c,
        Err(e) => return Err(ApiError::from(e))
    };
    return Ok(client);
}
