# Overview

EduLog is a very simple and small logging tool for Eduras. It is designed to help developers to analyze and filter console output. This includes error messages, info messages and much more.

EduLog is based on the Java built-in logging methods.


# From a programmer's point of view

For a detailed documentation, please consider the [JavaDoc](http://edurasgame.github.io/edulog).

## Building
You can use the provided ant-script (`build.xml`) to build a `jar`-file and include it into your project.


## Initializing

To enable EduLog, it must be initialized once (preferred in the main method). To do so, just call the `init()` method of EduLog.
By default, EduLog logs to `logfile.txt` in the current directory. If you want to use a custom output file, use the other variants of `init()`.

You may change the default logging thresholds (WARNING). To do so, call `setBasicLogLimit`, `setConsoleLogLimit` or
                    `setFileLogLimit` . Note, that the basic log limit must be below both others to make them apply.

An example can be found in the [eduras-repository](https://github.com/EdurasGame/eduras) in EdurasServer class.

## Logging

When coding, using EduLog is quite simple. Just don't use `System.out` anymore. Instead, use EduLog's builtin static methods that are quite easy to understand.

First, every class that uses logging requires a static call to retrieve its logger, for example:
```java
private final static Logger L = EduLog.getLoggerFor(ObjectFactory.class.getName());
```

Replace `ObjectFactory` with the name of your class.

Then, you can use the `L` instance to log:

-   `info(String s)` used for info messages (e.g. "listening on port x").

-   `warning(String s)` used for warning messages (e.g. "player already existing").

-   `severe(String s)` used for error messages (e.g. "could not bind socket").

-   `fine(String s)` used for debug messages and other useless notifications :)

-   `log(Level l, String s, Throwable t)` used to append an exception to your message. Use this in catch-blocks for example.

To pass exceptions, use the last method. The following example includes exception passing and also localization:
```java
try {
    server.start();
} catch (ServerNotReadyForStartException e) {
    L.log(Level.SEVERE, Localization.getStringF("Server.notready", e.getMessage()), e);
    return;
}
```

# License
Copyright 2016 Florian Mai and Jannis Mell

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
