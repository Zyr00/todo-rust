Todo App
============

A simple todo app, this project has made so that i could learn [rust][1].

A REST API made in [rust][1] with the framework [actix][3].
The database made with [postgres][2].

All of this is containerized with [docker][4] and [docker-compose][5].

Install and Build
============

With [docker][4]
 - `git clone https://github.com/Zyr00/todo-rust.git todo`;
 - `cd todo`;
 - `docker-compose up`.

Without [docker][4]
 - `git clone https://github.com/Zyr00/todo-rust.git todo`;
 - `cd todo/rest_api`;
 - edit `.env` file to setup the correct credentials for [postgres][2];
 - `cargo install cargo-watch` required if `./start.sh` is used;
 - `cargo build`;
 - `cargo run` or `./start.sh` for auto reloading.


[1]: <https://rust-lang.org> "Rust"
[2]: <https://postgresql.org> "Postgres"
[3]: <https://actix.rs> "Actix"
[4]: <https://docker.com/> "Docker"
[5]: <https://docs.docker.com/compose/> "Docker Compose"
