FROM rust:1.53-slim

WORKDIR app
COPY . .
RUN cargo install systemfd cargo-watch
RUN cargo build

CMD ["./start.sh"]
