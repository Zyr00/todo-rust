#!/bin/bash
systemfd --no-pid -s http::0.0.0.0:5000 -- cargo watch -x run
#cargo run
