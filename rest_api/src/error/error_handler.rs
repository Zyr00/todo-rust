use actix_web::{ http::StatusCode, ResponseError, HttpResponse };
use postgres::Error;
use serde::Deserialize;
use serde_json::json;
use std::fmt;

#[derive(Debug, Deserialize)]
pub struct ApiError {
    pub status_code: u16,
    pub message: String
}

impl ApiError {
    pub fn error(status_code: u16, message: &str) -> ApiError {
        ApiError { status_code, message: message.to_string() }
    }
}

impl fmt::Display for ApiError {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        f.write_str(self.message.as_str())
    }
}

impl From<Error> for ApiError {
    fn from(error: Error) -> ApiError {
        let val = error.to_string();
        let error = match error.as_db_error() {
            Some(err) => err.message(),
            None => &val
        };

        ApiError {
            message: error.to_string(),
            status_code: StatusCode::INTERNAL_SERVER_ERROR.as_u16()
        }
    }
}

impl ResponseError for ApiError {
    fn error_response(&self) -> HttpResponse {
        let status_code = match StatusCode::from_u16(self.status_code) {
            Ok(status) => status,
            Err(_) => StatusCode::INTERNAL_SERVER_ERROR
        };

        let message = match self.message.chars().count() > 0 {
            true => self.message.clone(),
            false => {
                match status_code.as_u16() < 500 {
                    true => "An unexpected error has ocurred".to_string(),
                    false => "Internal Server Error".to_string()
                }
            }
        };

        HttpResponse::build(status_code)
            .json(json!({ "message": message }))
    }
}
