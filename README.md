# play-nativedb plugin

This module provides a native DB subsystem for Play! Framework 1 REST applications.

It uses the native JPA API internally and doesn't require any other library.

# Features

- Generic DAO structure for native (pure SQL) queries, with criteria API, result mapping, sorting and grouping

- Filtering on arbitrary columns types. Provides a few filters (strings, numbers, dates...), you can extend with your own filters.

- Support for paginated lists

- Wrapper functions for DB compatibility (H2, PostgreSQL, MySQL)
 
# How to use

####  Add the dependency to your `dependencies.yml` file

```
require:
    - nativedb -> nativedb 0.7.3

repositories:
    - nativedb:
        type:       http
        artifact:   "http://release.sismics.com/repo/play/[module]-[revision].zip"
        contains:
            - nativedb -> *
```

# License

This software is released under the terms of the Apache License, Version 2.0. See `LICENSE` for more
information or see <https://opensource.org/licenses/Apache-2.0>.
