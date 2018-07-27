# speedment-slf4j-adapter
Writes all Speedment logs to the default SLF4J Logging system, like in Spring.

## Installation
**With Maven:**
```xml
<dependency>
  <groupId>com.github.pyknic</groupId>
  <artifactId>speedment-slf4j-adapter</artifactId>
  <version>1.0.0</version>
</dependency>
```

**With Gradle:**
```xml
compile 'com.github.pyknic:speedment-slf4j-adapter:1.0.0'
```

## Usage
Add a file to your project that sets the Speedment `LoggerFactory` to `new Slf4jLoggerFactory()`. For an example, this is how it could look in Spring:
```java
package com.yourcompany;

import com.speedment.common.logger.LoggerManager;
import com.github.pyknic.speedmentslf4j.Slf4jLoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration // Needs to be loaded at runtime
public class SpeedmentSlf4jAdapter() {
    static {
        LoggerManager.setFactory(new Slf4jLoggerFactory());
    }
}
```

## License
Copyright 2018 Emil Forslund

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
