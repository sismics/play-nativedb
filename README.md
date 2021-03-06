[![GitHub release](https://img.shields.io/github/release/sismics/play-nativedb.svg?style=flat-square)](https://github.com/sismics/play-nativedb/releases/latest)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# play-nativedb plugin

This module provides a native DB subsystem for Play! Framework 1 REST applications.

It uses the native JPA API internally and doesn't require any other library.

# Features

- Generic DAO structure for native (pure SQL) queries, with criteria API, result mapping, sorting and grouping

- Filtering on arbitrary columns types. Provides a few filters (strings, numbers, dates...), you can extend with your own filters.

- Support for paginated lists

- Wrapper functions for DB compatibility (H2, PostgreSQL, MySQL)
 
- Admin console to debug queries in realtime
 
# How to use

####  Add the dependency to your `dependencies.yml` file

```
require:
    - nativedb -> nativedb 1.2.0

repositories:
    - sismicsNexusRaw:
        type: http
        artifact: "https://nexus.sismics.com/repository/sismics/[module]-[revision].zip"
        contains:
          - nativedb -> *
```

# Enable the admin console

The admin console allows you to monitor queries in realtime.

Add the following parameter to enable the admin console:

```
nativedb.console.enabled=true
```

Note: the admin console is enabled by default in Dev mode.

### Secure the admin console

Add the following parameter to secure the admin console

```
nativedb.console.username=console
nativedb.console.password=pass1234
```

# License

This software is released under the terms of the Apache License, Version 2.0. See `LICENSE` for more
information or see <https://opensource.org/licenses/Apache-2.0>.
