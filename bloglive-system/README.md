# BlogLive 系统

BlogLive 是一个使用 Java 和 H2 数据库构建的轻量级博客系统。它支持大文件处理、用户认证和基本的日志记录功能。本文档提供了关于如何设置、配置和使用 BlogLive 系统的详细指南。

## 功能特性
- **嵌入式 H2 数据库**：使用 H2 数据库存储数据。
- **文件上传和下载**：支持大文件的上传和下载。
- **用户认证**：提供基本的用户认证功能。
- **流量日志**：记录用户活动和流量数据。
- **RESTful API**：提供多种操作的 API 接口。

## 系统要求
- **Java 11 或更高版本**：确保已安装 Java 11 或更高版本。
- **Maven**：用于构建和管理依赖项。
- **H2 数据库驱动**：H2 数据库驱动程序包含在 `lib` 目录中。

## 目录结构
bloglive-system/
├── src/
│   └── com/
│       └── bloglive/
│           ├── DatabaseManager.java
│           ├── FileUtils.java
│           ├── EncryptionUtils.java
│           ├── LogManager.java
│           ├── AuthManager.java
│           └── BlogLiveServer.java
├── lib/
│   └── h2-1.4.199.jar
├── .gitignore
├── README.md
└── pom.xml


## 安装指南
1. **克隆代码仓库**
    ```bash
    git clone https://github.com/your-repository/bloglive-system.git
    cd bloglive-system
    ```
    （注意：由于网络原因，上述链接可能无法成功解析，请检查链接的合法性，适当重试。）

2. **安装 Maven 依赖项**
    ```bash
    mvn clean install
    ```
    此命令将下载所需的依赖项（包括 H2 数据库驱动程序）并构建项目。

3. **配置项目**
    - **数据库配置**：项目使用嵌入式 H2 数据库，数据库文件将存储在用户的主目录下（`~/BlogLive.h2.db`）。
    - **环境变量**：本项目无需额外配置环境变量。

4. **启动应用程序**
    ```bash
    mvn exec:java -Dexec.mainClass="com.bloglive.BlogLiveServer"
    ```
    此命令将启动 BlogLive 服务器，默认端口为 4567。如果需要更改端口，可以在 `pom.xml` 文件中设置 `server.port` 属性。

## 使用方法
1. **上传文件**
    ```bash
    curl -X POST -d /path/to/file.txt http://localhost:4567/upload
    ```
    此命令将文件上传到服务器。文件将存储在数据库的 `Files` 表中。

2. **下载文件**
    ```bash
    curl -X GET http://localhost:4567/file/1
    ```
    此命令从服务器检索文件。文件将从数据库的 `Files` 表中获取并下载到当前目录。

3. **访问用户端点**
    ```bash
    curl -X GET http://localhost:4567/users/admin
    ```
    此命令检索指定用户名的用户信息。

## 配置说明
1. **数据库初始化**
    `DatabaseManager` 类在应用程序启动时初始化数据库表。如果需要单独初始化数据库，可以运行以下命令：
    ```bash
    mvn exec:java -Dexec.mainClass="com.bloglive.DatabaseManager"
    ```

2. **文件上传和下载**
    - **文件上传**：`FileUtils` 类处理文件上传。文件存储在数据库的 `Files` 表中。
    - **文件下载**：`FileUtils` 类处理文件下载。文件从数据库的 `Files` 表中检索。

3. **用户认证**
    `AuthManager` 类处理用户认证。可以添加更复杂的认证逻辑，例如密码哈希和 JWT 令牌。

4. **日志记录**
    `LogManager` 类处理日志记录。日志将写入项目根目录下的 `bloglive.log` 文件。

---

# 将 BlogLive 数据库嵌入到博客网站中

以下是将 BlogLive 数据库嵌入到博客网站中的具体步骤和建议：

## 1. 系统环境准备
- **Java 开发环境**：确保已安装 Java 11 或更高版本。
- **Maven 工具**：用于构建和管理项目依赖。
- **数据库环境**：确保 H2 Database 或 SQLite 已安装并配置好。

## 2. 下载并配置 BlogLive 系统
- **下载 BlogLive 代码**：从 GitHub 或其他源码托管平台下载 BlogLive 的代码仓库。
- **解压代码**：将下载的代码解压到本地工作目录。
- **配置数据库**：
    - 如果使用 H2 Database，确保数据库配置文件 `h2-1.4.199.jar` 存在于项目的 `lib` 目录中。
    - 如果使用其他数据库，修改 `DatabaseManager.java` 中的数据库连接字符串和配置。

## 3. 集成到博客网站
- **引入依赖**：确保博客网站的项目中引入了 BlogLive 系统的依赖项。如果使用 Maven，将以下依赖项添加到项目的 `pom.xml` 文件中：
    ```xml
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>1.4.199</version>
    </dependency>
    <dependency>
        <groupId>com.sparkjava</groupId>
        <artifactId>spark-core</artifactId>
        <version>2.9.3</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.32</version>
    </dependency>
    ```

- **整合代码**：将 BlogLive 系统的代码整合到博客网站的代码中。
    - 将 `DatabaseManager`、`FileUtils`、`EncryptionUtils`、`LogManager` 和 `AuthManager` 类添加到博客网站的项目中。
    - 确保博客网站的代码能够正确调用这些类的方法。

- **数据库初始化**：
    - 在博客网站的初始化阶段，调用 `DatabaseManager.initializeDatabase()` 方法初始化数据库。
    - 如果博客网站需要支持大文件处理，确保文件存储路径和数据库表结构与 BlogLive 系统兼容。

- **用户认证**：
    - 如果博客网站已有用户认证系统，可将其与 BlogLive 系统的 `AuthManager` 类进行整合。
    - 如果博客网站没有用户认证系统，可直接使用 BlogLive 系统提供的用户认证功能。

- **流量日志**：
    - 在博客网站的合适位置调用 `LogManager.log()` 方法记录用户活动和流量数据。
    - 配置日志存储和分析工具，如 ELK 堆栈或日志分析软件，以便更好地监控和分析流量数据。

## 4. 测试与部署
- **单元测试**：编写单元测试用例，确保 BlogLive 系统的各个功能模块在博客网站中正常工作。
- **集成测试**：对博客网站的整体功能进行集成测试，确保 BlogLive 系统与博客网站的其他功能模块协同工作。
- **性能测试**：对博客网站进行性能测试，确保 BlogLive 系统不会对网站的性能产生负面影响。
- **部署**：将博客网站部署到生产环境，确保 BlogLive 系统的数据库和文件存储路径配置正确，并能够正常访问。

## 5. 维护与更新
- **定期备份**：定期备份 BlogLive 系统的数据库和文件存储，防止数据丢失。
- **更新依赖**：定期更新 BlogLive 系统和博客网站的依赖项，确保系统的安全性和稳定性。
- **监控与优化**：持续监控博客网站的性能和流量数据，根据需要对 BlogLive 系统进行优化。
- **用户反馈**：收集用户反馈，根据用户需求和建议对 BlogLive 系统和博客网站进行改进。

通过以上步骤，您可以将 BlogLive 数据库成功嵌入到博客网站中，为用户提供更加丰富和强大的功能体验。

---

## 贡献指南
如果您想为 BlogLive 项目做出贡献，请按照以下步骤操作：
1. **fork 仓库**：在 GitHub 上 fork 该项目。
2. **新创建分支**：为您的功能或错误修复创建一个新分支。
3. **提交更改**：提交您的更改，并附上描述性的提交信息。
4. **推送到您的分支**：将您的更改推送到 GitHub 上的分支。
5. **创建拉取请求**：从您的分支向主仓库创建一个拉取请求。

## 许可证
本项目采用 MIT 许可证(Bilibili:纸张鸭)
