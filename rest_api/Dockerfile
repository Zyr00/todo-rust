FROM rust:1.53-slim

WORKDIR app
COPY . .
RUN cargo install cargo-watch
RUN cargo build

CMD ["./start.sh"]
