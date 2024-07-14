# Changes

## 1.5.0-RC3 / YYYY-MM-DD

* Add Method `RequestUtils.isRequestCancelled`
* Fix for `#32 - html:cancel taglib not working in case of MultipartRequestWrapper` thanks to nrmnrm
* Tiles: Correct `I18nFactorySet.initFactory` under windows
* Set Version to 1.5.0-SNAPSHOT
* Update documentation to version 1.5.0

## 1.5.0-RC2 / 2024-03-01

* Set Version to 1.5.0-RC2
* Update `README.md` to version 1.5.0-RC2
* Correct file-upload-example
* Add missing `maven-release-plugin` 3.0.1
* Add missing `maven-antrun-plugin` 3.1.0
* Add `versions-maven-plugin` 2.16.2 for updates reports
* Bump `webdrivermanager` from 5.6.2 to 5.7.0
* Bump `htmlunit` from 3.9.0 to 3.11.0
* Bump `cargo-maven3-plugin` from 1.10.11 to 1.10.12
* Bump `maven-jxr-plugin` from 3.3.1 to 3.3.2
* Bump `checkstyle` from 10.12.6 to 10.14.0
* Bump `junit-jupiter-api` from 5.10.1 to 5.10.2
* Bump `slf4j-api`, `slf4j-simple` and `jcl-over-slf4j` from 2.0.9 to 2.0.12
* Bump `selenium-api` and `selenium-java` from 4.16.0 to 4.18.1
* Bump `groovy` from 4.0.17 to 4.0.18
* Bump `maven-failsafe/surefire-[report]-plugin` from 3.2.3 to 3.2.5
* Security - Digester: Resolving XML external entity in user-controlled data
* Fix for `CVE-2023-49735` Apache Tiles: Unvalidated input may lead to path traversal and XXE
* Fix for `CVE-2023-34396` Apache Struts vulnerable to memory exhaustion
* Exclude `doc-files` from export-packages in `MANIFEST.MF`
* Add filtering for Option `text` when `filter` is set to `true`
* Change cryptographic algorithm from `AES` to `SHA-256` for token generation
* Correct OSGi/JPMS name from integration-test-apps
* Ensure Input-/OutputStream is closed and use try-with-resources
* Fix for Apache Struts `CVE-2012-1007` Multiple Cross Site Scripting Vulnerabilities (Sample apps)
* Add `CVE-2008-2025` info to `README`
* JS validate email remove useless regular-expression character escape
* Change cryptographic algorithm from `MD5` to `AES` for token generation
* Add example for multiple-file-upload
* Improvements in FileUpload handling
* Add property `multiple` to `html:file`, `html-el:file` and `nested:file`
* Set Version to 1.5.0-RC2-SNAPSHOT

## 1.5.0-RC1 / 2023-12-28

* Set Version to 1.5.0-RC1
* Corr: Skip `bnd-maven-pluin` in integration-tests
* Update documentation and `README.md` to version 1.5.0-RC1
* Update minimum MAVEN-Version from 3.5.4 to 3.8.1
* Replace `maven-bundle-plugin` 5.1.9 with `bnd-maven-plugin` 7.0.0
* Comment out `taglib-maven-plugin` because it is not adapted to `Jakarta Server Pages 3.0`
* Bump `spotbugs-annotations` from 4.8.2 to 4.8.3
* Bump `maven-compiler-plugin` from 3.11.0 to 3.12.1
* Bump `groovy` from 4.0.16 to 4.0.17
* Bump `maven-failsafe/surefire-[report]-plugin` from 3.2.2 to 3.2.3
* Bump `webdrivermanager` from 5.6.1 to 5.6.2
* Bump `spotbugs-annotations` from 4.8.0 to 4.8.2
* Bump `htmlunit` from 3.7.0 to 3.9.0
* Bump `commons-validator` from 1.7 to 1.8.0
* Bump `commons-io` from 2.15.0 to 2.15.1
* Bump `cargo-maven3-plugin` from 1.10.10 to 1.10.11
* Bump `maven-project-info-reports-plugin` from 3.4.5 to 3.5.0
* Bump `checkstyle` from 10.12.4 to 10.12.6
* Bump `maven-javadoc-plugin` from 3.6.0 to 3.6.3
* Bump `junit-jupiter-api` from 5.10.0 to 5.10.1
* Bump `jsf` from 3.0.2 to 3.0.5
* Bump `selenium-api` and `selenium-java` from 4.15.0 to 4.16.1
* Bump `groovy` from 4.0.15 to 4.0.16
* Bump `maven-failsafe/surefire-[report]-plugin` from 3.2.1 to 3.2.2
* Remove duplicate slf4j-providers in `taglib`
* Correct Javadoc-warnings
* Centralize Javadoc-configurations
* Better Javadoc-Dependency-Links
* Corr: `index.xml` - add missing `release-notes-1_4_5.xml` to index
* Corr: `CommonsMultipartRequestHandler.addFileParameter` thanks to jo-kl
* Bump `tiles-request-jakarta-servlet` from 1.0.0-SNAPSHOT to 1.0.0
* Bump `weld-servlet-shaded` from 3.1.9.Final to 4.0.3.Final
* Change `tiles-request-servlet` to `tiles-request-jakarta-servlet` 1.0.0-SNAPSHOT
* Change `.gitignore`
* Further updates to the documentation
* Corr: `RequestUtils`
* Corr: Fix links in `preface`-doc
* Corr: Remove double-defined dependency
* Further adaptations to `fileupload2`
* Change API `MultipartRequestHandler`: return HashMap instead Hashtable
* Some reformatings
* Upgrade to org.apache.commons.fileupload2
* Change all `javax.*`- to `jakarta.*`-namespaces from groovy-examples
* Upgrade `jsf` from 2.3.18 to 3.0.2
* Bump `jstl` from 1.2.7 to 2.0.0
* Bump `el-api` from 3.0.3 to 4.0.0
* Upgrade `jsp-api` from 2.3.6 to 3.0.0
* Upgrade `servlet-api` from to 4.0.4 to 5.0.0
* Change all `javax.*`- to `jakarta.*`-namespaces with `Tomcat Migration Tool 1.0.5`
* Use `tomcat` 10.0.27 instead 9.0.68 for integration-tests
* Update `README.md` for next release
* Set Version to 1.5.0-SNAPSHOT

## 1.4.5 / 2023-11-03

* Set Version to 1.4.5
* Update issues-links in documentation
* Set documentation-skin to `maven-fluido-skin`
* Bump `webdrivermanager` from 5.6.0 to 5.6.1
* Update `htmlunit` from 3.6.0 to 3.7.0
* Bump `maven-pmd-plugin` from 3.21.0 to 3.21.2
* Bump `commons-chain` from 1.3-SNAPSHOT to 1.3.0
* Bump `selenium-api` and `selenium-java` from 4.14.1 to 4.15.0
* Add contributor and add more informations to me
* Update `README.md`
* Rename POM-property `encoding` to `sourceEncoding`
* Add new property `controller.fileCountMax` due `commons-fileupload` 1.5
* Reformat some JavaDocs
* Bump `maven-failsafe/surefire-[report]-plugin` from 3.1.2 to 3.2.1
* Bump `maven-jxr-plugin` from 3.3.0 to 3.3.1
* Bump `maven-dependency-plugin` from 3.6.0 to 3.6.1
* Bump `maven-clean-plugin` from 3.3.1 to 3.3.2
* Bump `maven-checkstyle-plugin` from 3.3.0 to 3.3.1
* Bump `webdrivermanager` from 5.5.3 to 5.6.0
* Bump `commons-io` from 2.14.0 to 2.15.0
* Update documentation for version 1.4.5
* Bump `spotbugs-annotations` from 4.7.3 to 4.8.0
* Bump `junit-jupiter-api` from 5.9.1 to 5.10.0
* Bump `commons-io` from 2.11.0 to 2.14.0
* Bump `commons-fileupload` from 1.4 to 1.5
* Bump `cargo-maven3-plugin` from 1.9.13 to 1.10.10
* Bump `maven-bundle-plugin` from 5.1.7 to 5.1.9
* Bump `maven-war-plugin` from 3.3.2 to 3.4.0
* Bump `maven-project-info-reports-plugin` from 3.3.0 to 3.4.5
* Bump `maven-pmd-plugin` from 3.17.0 to 3.21.0
* Bump `maven-jxr-plugin` from 3.2.0 to 3.3.0
* Bump `maven-jar-plugin` from 3.2.2 to 3.3.0
* Bump `maven-source-plugin` from 3.2.1 to 3.3.0
* Bump `maven-site-plugin` from 3.12.0 to 3.12.1
* Bump `maven-scm-publish-plugin` from 3.2.1 to 3.1.0
* Bump `maven-resources-plugin` from 3.2.0 to 3.3.1
* Bump `maven-javadoc-plugin` from 3.4.0 to 3.6.0
* Bump `maven-install-plugin` from 3.0.0-M1 to 3.1.1
* Bump `maven-gpg-plugin` from 3.0.1 to 3.1.0
* Bump `maven-enforcer-plugin` from 3.1.0 to 3.4.1
* Bump `maven-deploy-plugin` from 3.0.0-M2 to 3.1.1
* Bump `maven-dependency-plugin` from 3.3.0 to 3.6.0
* Bump `maven-compiler-plugin` from 3.10.1 to 3.11.0
* Bump `maven-clean-plugin` from 3.2.0 to 3.3.1
* Bump `checkstyle` from 10.4 to 10.12.4 and adapt `struts_checks.xml`
* Bump `maven-checkstyle-plugin` from 3.1.2 to 3.3.0
* Bump `maven-assembly-plugin` from 3.4.1 to 3.6.0
* Bump `jaxb-runtime` from 2.3.7 to 2.3.9
* Bump `jakarta.faces` (`mojarra`) from 2.3.18 to 2.3.21
* Bump `antlr` and `antlr4-maven-plugin` from 4.11.1 to 4.13.1
* Bump `slf4j-api`, `slf4j-simple` and `jcl-over-slf4j` from 2.0.3 to 2.0.9
* Bump `selenium-api` and `selenium-java` from 4.6.0 to 4.14.1
* Bump `groovy` from 4.0.6 to 4.0.15
* Bump `maven-failsafe/surefire-[report]-plugin` from 3.0.0-M7 to 3.1.2
* #11: add html-code to test it
* #11: further improvements in the "filter" method, reformatting, one more test case
* #11: improved entity handling in "filter" method
* #11: added another test case
* #11: fixed regex, added more test cases
* #11: bugfix for issue #11 (skip xml entities in filter method)
* Update `tomcat` from 9.0.68 to 9.0.82
* Update `htmlunit` from 2.66.0 to 3.6.0
* Corr: Adapt to weblegacy commons-chain - missing `struts-tiles` and `stuts-tiles2`
* Recover logging when executing an ActionCommandBase-class
* Adapt to weblegacy commons-chain
* Replace apache commons-chain 1.2 with weblegacy commons-chain 1.3

## 1.4.4 / 2022-11-14

* Set Version to 1.4.4
* Some updates in `README.md`
* Update documentation for version 1.4.4
* Set all XML-encodings to UTF-8
* Adapt JavaDoc-configuration
* Adapt dependency-scopes like JakartaEE 8
* Bump `jaxb-runtime` from 2.3.1 to 2.3.7 to use `jakarta`-version
* Faces-Example1 & 2: Use new CDI
* Add `weld-servlet-shaded` for CDI-function for JSF 2.3
* Replace JSF-implementation `myfaces` 2.3.10 with `mojarra` 2.3.18
* Removing the use of `myfaces`-implementation-specifics
* Update `taglibs-standard-spec` 1.2.5 to `jakarta.servlet.jsp.jstl` 1.2.7
  and change implementation from `org.apache.taglibs` to `org.glassfish.web`
* Update `javax.el-api` 3.0.0 to `jakarta.el-api` 3.0.3
* Update `javax.servlet.jsp-api` 2.3.3 to `jakarta.servlet.jsp-api` 2.3.6
* Update `javax.servlet-api` 4.0.1 to `jakarta.servlet-api` 4.0.4
* Update `web.xml` to version 4.0
* Add `jaxb-api` and `jaxb-runtime` because JDK 11
* Faces-Example1 & 2: Undo use CDI because native JSF-CDI is deprecated
* Bump `myfaces` from 2.2.15 to 2.3.10
* Resolve many JDK 19 compiler-warnings
* Remove compiler-warnings till JDK 17
* Upgrade `servlet-api` from 3.1.0 to 4.0.1
* Update `README.md` for next release
* Use `tomcat` 9.0.68 instead 8.5.83 for integration-tests
* Set Version to 1.4.4-SNAPSHOT

## 1.4.3 / 2022-11-10

* Change JavaEE-doc-link from 6 to 7
* Set Version to 1.4.3
* Update documentation for version 1.4.3
* Resolve deprecated java-code in jsp's
* Faces-Example1&2: Correct expired-password-processing
* Faces-Example2: Adaption to run with JSF 2.2
* Faces-Example1&2: Use CDI instead `faces-config` for managed-beans
* Faces-Example2: Adaption in `web.xml`
* Faces-Example2: For `faces-config` use CDI
* Upgrade `faces-config` to 2.2-version
* Bump `myfaces` from 2.0.25 to 2.2.15
* Correct `ValidWhenEvaluator` - undo replace generic-wildcards
* Bump `el-api` from 2.2.5 to 3.0.0
* Bump `jsp-api` from 2.2.1 to 2.3.3
* Remove some `@SuppressWarnings`
* Replace some generic-wildcards
* Upgrade `servlet-api` from 3.0.1 to 3.1.0
* Move all `org.apache.struts.mock`-classes in module `core` from main into test
* Update `README.md` for next release
* Bump `webdrivermanager` from 5.3.0 to 5.3.1
* Bump `selenium-api` and `selenium-java` from 4.5.3 to 4.6.0
* Use `tomcat` 8.5.83 instead 7.0.109 for integration-tests
* Set Version to 1.4.3-SNAPSHOT

## 1.4.2 / 2022-11-03

* Correct typo in `release-notes`
* Set Version to 1.4.2
* Update `roadmap.xml`
* Update version- and upgrade-status in `README.md`
* Bump `htmlunit` from 2.65.1 to 2.66.0
* Bump `checkstyle` from 10.3.4 to 10.4
* Bump `selenium-api` and `selenium-java` from 4.5.0 to 4.5.3
* Adapt `tiles-config_1_*.dtd` so that DTDDoc works
* Correct some javadoc-warnings
* Add release-notes for version 1.4.2
* Add section `breaking changes` to release-notes since 1.3.11
* Add `supportIncrementalBuild`-config to `maven-bundle-plugin` to resolve eclipse-warning
* Apps: Add dependencies to resolve jsp-warnings
* Resolve some jsp-warnings
* Bump `myfaces` from 1.2.12 to 2.0.25
* Add javadoc-links to `servlet-api`, `jsp-api` and `el-api`
* Adjustment of dependencies
* Bump `jsp-api` from 2.1 to 2.2.2 and add `el-api` 2.2.5
* Upgrade `web.xml`s of `apps` from 2.5 to 3.0
* Correct typos
* Remove some `SuppressWarnings`
* Bump `servlet-api` from 2.5 to 3.0.1
* Add missing `slf4j-simple` to unit-tests (mailreader-dao, faces, taglib)
* Use `tomcat` 7.0.109 instead 6.0.53 for integration-tests
* Update `README.md` for next release
* Bump `spotbugs-annotations` from 4.7.2 to 4.7.3
* Bump `groovy` from 4.0.5 to 4.0.6
* Add missing slf4j-simple to integration-tests
* Standardize log variables
* Replace `commons-logging` with `slf4j` and `slf4j-simple`
* Apps: Replace `commons-logging` with `slf4j`
* Faces: Replace `commons-logging` with `slf4j`
* Tiles2: Replace `commons-logging` with `slf4j`
* Tiles: Replace `commons-logging` with `slf4j`
* Taglib: Replace `commons-logging` with `slf4j`
* Scripting: Replace `commons-logging` with `slf4j`
* Extras: Replace `commons-logging` with `slf4j`
* Core: Replace `commons-logging` with `slf4j`
* Bump `checkstyle` from 9.3 to 10.3.4
* Bump `antlr` from 4.9.3 to 4.11.1
* Set minimum JDK-Version to 11
* Set Version to 1.4.2-SNAPSHOT

## 1.4.1 / 2022-10-11

* Set Version to 1.4.1
* Update upgrade-status in `README.md`
* Add version to checkin-comment to `maven-scm-publish-plugin`
* Bump `spotbugs-annotations` from 4.7.1 to 4.7.2
* Bump `htmlunit` from 2.64.0 to 2.65.1
* Bump `junit-jupiter-api` from 5.9.0 to 5.9.1
* Bump `selenium-java` from 4.4.0 to 4.5.0
* Bump `groovy` from 4.0.4 to 4.0.5
* Add release-documentation for 1.4.1
* Add release-documentation for 1.4.0
* Add versions-overview to `README.md`
* Add release-documentation for 1.3.11
* Move `struts-mailreader-dao` into `struts-apps` and adapt documentations
* Use `struts-mailreader-dao` in `faces-examples`
* Rework dependencies-usages
* Rework `slf4j`-dependencies-usage
* Correct `tld`-files from `faces-examples`
* Add `tld`-documentation of `faces-examples`
* Use the new `srcDir`-Parameter in the `taglib-maven-plugin`
* Remove unused `cargo-maven3-plugin` at `apps/pom.xml`
* Tipfix at `el-example/logic-present.jsp`
* Use `digester` instead `digester3` to optimize dependencies
* Corrections at TLD-parameters
* Resolve compiler-warnings
* Resolve javadoc-warnings
* Add `Automatic-Module-Name` to `MANIFEST.MF`-files
* Prepare `struts-extras` for JPMS (add `extras` to packages)
* Small reformats in `tld`-files
* Correct `strutsel` to work with JSP 2.1
* Tipfix in `README.md`
* Bump `apache tiles` from 2.0.7 to 3.0.8
* Comment out forgotten debug-output
* Downgrade `antlr` from 4.10.1 to 4.9.3 because JDK-1.8
* Trim lines
* Bump `antlr` from 2.7.7 to 4.10.1 and always generate grammar
* Correct renderer-type in `LoadMessagesTag`
* Bump `groovy` from 3.0.9 to 4.0.4
* Replace `bsf`-dependency with JSR-223 `javax.script`
* Bump `groovy` from 1.0 to 3.0.9
* Fix groovy-compile-error
* Replace tabs with spaces
* Fix `maven-bundle-plugin` warning during compiling
* Fix compile-warning at `CommandLinkRenderer`
* Add `JavascriptValidatorComponent`-Constructor with default values.
* Convert `LoadMessages`-JSF-Tag to JSF 1.2
* Tiles-Version as POM-Property
* Correct some typos in JavaDocs and trim lines
* Upgrade `jsf` (`myfaces`) from 1.0.9 to 1.2.12
* Upgrade all taglibs to 2.1 (except faces to 2.0)
* Little re-formating in `ELImgTag`
* Corr: Remove `xhtml`-tag from `struts-html-el` duplicated in `struts-html`
* Bump `webdrivermanager` from 5.2.3 to 5.3.0
* Bump `taglibs` from 1.1.2 to 1.2.5
* Adapt `EvalHelper` to jsp 2.1 and remove `tagObject`-parameter
* Bump `jsp-api` from 2.0 to 2.1
* Bump `bsf` from 2.3.0 to 2.4.0
* Bump `servlet-api` from 2.4 to 2.5
* Bump `jstl` and `taglibs.standard` from 1.0.2 to 1.1.2
* Change deprecated value `EVAL_BODY_TAG` to `EVAL_BODY_BUFFERED`
* Bump `servlet-api` from 2.3 to 2.4
* Change group-id of `jsp-api` from `javax.servlet` to `javax.servlet.jsp`
* Apps-Blank: Add missing dependency
* Replace tabs with spaces
* Clear all compiler-warnings
* Revision of the dependencies based on the dependency analysis
* IT: Remove unnecessary JDK-1.7-wrapper
* Bump `selenium-java` from 4.3.0 to 4.4.0
* Bump `commons-digester` from 1.8 to 3.2
* Bump `webdrivermanager` from 5.2.1 to 5.2.3
* Bump `htmlunit` from 2.63.0 to 2.64.0
* Bump `commons-codec` from 1.2 to 1.15
* Correct `pom.xml` in `struts-it` - remove `dependencyManagement`
* Correct `README.md` at profile `cargorun`
* Solve deprecated in `struts-tiles2`
* Bump `commons-fileupload` from 1.2.1 to 1.4
* Bump `commons-io` from 1.3.2 to 2.11.0
* Bump `commons-collections` from 2.1 to 4.4
* Change `StringBuffer` to `StringBuilder`
* Fix: CVE-2016-1181 and CVE-2016-1182
* Fix: CVE-2015-0899 with test-cases
* Bump `tiles*` from 2.0.5 to 2.0.7, remove JDK-1.4 support dependencies
* Some corrections in examples-app
* Bump `commons-validator` from 1.3.1 to 1.7, add old commons-validator-js from 1.4
* Bump `commons-logging` from 1.1.1 to 1.2
* JavaDoc: Correct links and others
* Correct generics-adaption in `PluginConfig`
* Correct link to preface
* Apps: HTML5-Updates
* Strip trailing spaces
* Replace tabs with spaces
* Adaptation of `package-info.java` so that output is similar to the old `package.html`
* Tip-Fix in JavaDoc (Evalute -> Evaluate)
* Apps: JDK8 - Add generics, serialVersionID, for-each-loop, ...
* Tiles2: Convert all `package.html` to `package-info.java`
* Tiles2: JDK8 - Add generics, serialVersionID, for-each-loop, ...
* Tiles: Convert all `package.html` to `package-info.java`
* Tiles: JDK8 - Add generics, serialVersionID, for-each-loop, ...
* Taglib: Convert all `package.html` to `package-info.java`
* Taglib: JDK8 - Add generics, serialVersionID, for-each-loop, ...
* Scripting: Convert all `package.html` to `package-info.java`
* Scripting: JDK8 - Add generics, serialVersionID, for-each-loop, ...
* Mailreader-DAO: JDK8 - Add generics, serialVersionID, for-each-loop, ...
* IT: Convert all `package.html` to `package-info.java`
* Faces: JDK8 - Add generics, serialVersionID, for-each-loop, ...
* Extras: Convert all `package.html` to `package-info.java`
* Extras: JDK8 - Add generics, serialVersionID, for-each-loop, ...
* El: Convert all `package.html` to `package-info.java`
* El: JDK8 - Add generics, serialVersionID, for-each-loop, ...
* Core: Convert all `package.html` to `package-info.java`
* JDK8: Add generics, serialVersionID, for-each-loop, remove warnings, ...
* Fix: CVE-2014-0114 with test-cases
* Add `TestLogFactory` and `TestLog` for testing
* Bump `beanutils` from 1.8.0 to 1.9.4
* Set java-version from `1.4` to `1.8`
* Upgrade `Tomcat5x` to `Tomcat6x`
* Set Version to 1.4.1-SNAPSHOT

## 1.4.0 / 2022-07-28

* Set Version to 1.4.0
* Bump `junit-jupiter-engine` from 5.8.2 to 5.9.0
* Bump `maven-bundle-plugin` from 1.4.0 to 5.1.7 and remove `_nouses`-parameter
* Add `dormant`-profile to `README.md` in IT
* Strip trailing spaces
* Rename `tour.htm` to `tour.html`
* Replace tabs with spaces
* Some encoding-updates to UTF-8
* Delete `.cvsignore`-files
* Correct `README.md` - section deployment
* Correct title in site-documentation of `Tiles2`
* Remove `provided`-scope for `myfaces-jsf-api`
* Add `dependency-analyze`-report
* Create no MANIFEST with OSGi-Entries at IT
* Correct deprecated maven-properties
* Correct dependencies for `org.apache.tiles:*` and lib-assembly
* Add `Tiles2` to site-documentation
* Add missing xml-declaration and encoding
* Little reformating
* Correct package-description in `tiles` and `tiles2`
* Consider exception in `initModulePlugIns` in tiles test-case
* Replace invalid UTF-8 code with space
* Correct typo in `html-link.jsp`
* STR-2986: Add `Tiles2`-dependency to `assembly`-POM
* Correct some links in site-documentation
* STR-1305: Add <html:label> tag to output HTML label
* STR-3032: Copy unaccessed messages/errors to session if redirecting
* STR-3153: Adapt `README.md`
* STR-3168: Missing dispatching-constants
* STR-2740: Support for XHTML 1.1 and XHTML 2.0 etc.
* STR-3153: Optional build profile for dormant subprojects
* STR-2913: Message tags to expose count of messages
* STR-3152: Pull common dependencies into the parent Struts 1 POM
* STR-3107: Case-insensitive URI matching
* STR-3111: Allow <set-property> as a top-level element
* STR-3078: Extend action configs by action id
* STR-2499: Find required forward or throw exception
* STR-3052: ImgTag missing actionId support
* STR-1922: Add message parameter XML-escaping to <html:messages>
* STR-2072: [taglib] html:option etal need title attribute
* STR-1175: No inheritance of html:html xhtml="true" in included tiles
* STR-3025 and STR-3094: Select input based on convention over configuration and log error when input forward does not exist
* STR-3024: Added ERROR, INPUT, LOGIN, SUCCESS constants
* STR-3004: Update missing 1.4 DTD
* STR-2986: Build Struts 1 - Tiles 2 integration
* STR-3004: Create 1.4 DTD
* STR-286 and STR-1116: ActionForm populate and reset strategy
* STR-3168: Add dispatching support to Controller and Action configuration
* Set Version to 1.4.0-SNAPSHOT

## 1.3.11 / 2022-07-19

* Remove check-changes during verify-phase
* Set Version to 1.3.11
* Bump `taglib-maven-plugin` from 2.6-SNAPSHOT to 2.6
* Correct `README.md` for deployment
* Add link to site-documentation into `README.md`
* Add `maven-scm-publish-plugin` to deploy site-documentation
* Delete `.htaccess` in `userGuide`
* Remove parent-POM
* Correct SCM-addresses, url's, organization and distribution management
* Correct title of `Struts Mailreader DAO`
* Adaptation of the links to the new web-space
* Add `struts-mailreader-dao` to dependencies-index
* Correct assembly-definitions to add `apps-it-selenium` and `struts-mailreader-dao`
* Change groupId from `org.apache.struts` to `io.github.weblegacy`
* Correct javadoc-options for minimized output and no errors
* Add new hint to `README.md`
* Add missing `slf4j-simple`-test-dependency
* Bump `webdrivermanager` from 5.1.1 to 5.2.1
* Bump `selenium-java` from 4.1.3 to 4.3.0
* Bump `htmlunit` from 2.61.0 to 2.63.0
* Bump `cargo-maven3-plugin` from 1.9.11 to 1.9.13
* Bump `maven-surefire-report-plugin` from 3.0.0-M6 to 3.0.0-M7
* Bump `maven-surefire-plugin` from 3.0.0-M6 to 3.0.0-M7
* Bump `maven-project-info-reports-plugin` from 3.2.2 to 3.3.0
* Bump `maven-pmd-plugin` from 3.16.0 to 3.17.0
* Bump `maven-failsafe-plugin` from 3.0.0-M6 to 3.0.0-M7
* Bump `maven-enforcer-plugin` from 3.0.0 to 3.1.0
* Bump `maven-assembly-plugin` from 3.3.0 to 3.4.1
* Corr: Adapt JXR-generation to resamble 1.3.10-Version
* Add `Struts-Mailreader DAO`-site-doc-files
* Move `maven-changes-plugin`-definition to parent-POM
* Adapt JXR-generation to resamble 1.3.10-Version
* Adapt JavaDoc-generation to resamble 1.3.10-Version
* Correct DTD-copy
* Correct `site.xml` in apps
* Generate change-report in `scripting`-module
* Correct `README.md`
* Lint markdown-files
* Add project-encoding-properties
* Add quite-config to `javadoc-plugin`
* Also install/deploy parent-`site.xml`-files
* Correct some typos in site-doc-files
* Add `Struts-Core`-site-doc-files
* New body-links in `site.xml`-files
* Use own images in site-doc-files
* Correct some URLs in site-doc-files
* Some reformatings in site-doc-files
* Add XSD to all site-doc-files and correct them
* Add/update XML-Declaration with encoding
* Adapt internal links in site-docs
* Delete not used `navigation.xml`'s from FAQs and user-guide
* Add again deleted preface to user-guide
* Downgrade `checkstyle` from 10.2 to 9.3 to use it with Java 8
* Correct http-adress of the license
* Use new `taglib-maven-plugin` from `io.github.weblegacy`
* Bump `webdrivermanager` from 5.1.0 to 5.1.1
* Bump `htmlunit` from 2.60.0 to 2.61.0
* Bump `cargo-maven3-plugin` from 1.9.10 to 1.9.11
* Bump `maven-site-plugin` from 3.11.0 to 3.12.0
* Bump `maven-javadoc-plugin` from 3.3.2 to 3.4.0
* Bump `checkstyle` from 10.1 to 10.2
* Use `commons-io` 2.11.0 for integration-tests
* Add more informations into `README`s, remove `mvn_hints`
* Bump `maven-taglib-plugin` 2.4 to `taglib-maven-plugin` 2.5
* Generate Assemblies in package-phase
* apps/faces-examples: remove spaces in comments
* Add optional dependency `commons-io`
* Bump MAVEN-Plugins (clean, failsafe, surefire, surefire-report)
* STR-2802: Add a test case
* STR-2321 and STR-2319: Compare floats and doubles
* Update README
* Reorg integration-tests
* Correct `html:link`-Tag: params connectect with `&amp;` instead `&`
* apps/[scripting-]mailreader: Convert `Canoo WebTests` to `Selenium IDE`-tests
* apps/[scripting-]mailreader: Add missing gif and remove version from index-title
* Remove unnecessary taglib-definitions in `faces-example`s
* Correct `faces-example`s (wrong DOCTYPEs) and activate Struts-Faces-Examples again
* Remove unused import
* Some corrections in `src/site`-docs
* WW-1974 Add link to release notes form announcement. Correct link in archival copy of 1.2.7 release notes
* STR-2153: Reveal the offending bean and property name when iterating
* STR-1195: SelectTag should single select when "multiple" attribute equals false
* STR-3191: Filter attributes
* STR-2779: Better explain why invalid collection (thanks to Ralf Hauser)
* STR-2904: Verify only one instance of the plugin is loaded per module
* STR-2611: Cite the form name in validation exception message
* Reorder language specific ActionResources-properties
* STR-1809: Enhance log error message for when the Action cannot instantiate
* STR-3085: Configuration post-processing via plug-ins
* STR-1819: Warn if a form is not declared but later used
* Skip install- and deployment-phase for integration tests
* Move more property files into standard Maven resources folder
* Correct `html-img.jsp` in `apps/examples`
* STR-2700: Correction - Clear input stream on aborted upload (Windows only)
* Adjust resource definitions in pom.xml
* STR-2013: Add Spanish translations
* Move property files into standard Maven resources folder
* Remove NOTICE.txt from Apache Struts EL
* STR-3042: Retrieve page through methods
* STR-2098: Clarify FileNotFoundException depends on underlying implementation
* STR-2959: File upload has 2GB limit
* STR-2700: Clear input stream on aborted upload (Windows only)
* STR-3068: Warn when using the classic RequestProcessor
* STR-1870: Add debug log message to explain initialization strategy
* STR-1870: Fail fast when definition is duplicated
* STR-2924: Allow Exception key to be optional
* STR-2020: Explain that NPE is caused by missing ActionServlet
* STR-1638: Note that anchor cannot be standalone
* STR-3150: Use Chain processing constants
* STR-3119: Add root cause to thrown exceptions that do not accept root cause in the constructor
* STR-2437: Standardize exception rethrows to contain root cause
* Some test-code reformating, no change
* Core: Making some of the fail messages a bit more expressive
* Bump selenium-java from 4.1.2 to 4.1.3
* Bump htmlunit from 2.59.0 to 2.60.0
* Bump maven-jxr-plugin from 3.1.1 to 3.2.0
* Bump checkstyle from 10.0 to 10.1
* Continue processing on `JavaDoc`-error in profile `pre-assembly`
* Centralize profile `pre-assembly`
* STR-3121: Bump Commons Logging to 1.1.1
* STR-3059: Bump Commons FileUpload to 1.2.1
* STR-3152: Pull common dependencies into the parent Struts 1 POM
* IT: Add `refresh` to Selenium-Test
* IT: Update `README`
* IT: Add `WebDriverManager` and activate Selenium-Test
* IT: Correct Selenium-Test and add configurable port-number
* IT: Add methods for JDK 1.7-compatibility
* Convert `testgen4web` to `Selenium` integration tests
* Add `cargorun`-profile
* Add forgotten `dispatchValidator.zip` from apache-struts1-clone
* Integration-test: skip creating empty jar file
* Move integration-test-settings to integration-test-parent-POM
* Integration-tests with JUnit5 and deploy server with cargo-function
* Bump JUnit 3.8.1 to JUnit5 5.8.2
* Compile tests with JDK 1.8 and integration-tests with JDK 1.7
* Move `TestMockBase` in module `core` from main into test
* Centralize junit dependency
* Update ReadMe
* Update javadoc-links
* Change comment in checkstyle-config
* Update deprecated MAVEN-Var ${basedir} to ${project.basedir}
* Move maven-war-plugin from struts-app to parent
* Set current MAVEN-Plugins in subproject-profile
* Bump MAVEN-Plugins to current versions
* Upgrade Checkstyle to 10.0
* Move checkstyle-config to local
* Upgrade maven-assembly-plugin with adjustments to current version
* Disable 2 non-working integration-tests
* Upgrade cargo-maven2-plugin (with dependencies) to cargo-maven3-plugin
* Replace maven-antrun-plugin with maven-resources-plugin
* Add/Define missing maven-plugins
* Define Java-Version thru MAVEN-Var
* Add maven-resources-plugin: Define encoding
* Add maven-enforcer-plugin: Enforces required MAVEN-Version
* Update deprecated MAVEN-Var ${version}
* Centralize MAVAN-Plugins-Version with PluginManagement
* Replace org.apache.struts with ${project.groupId}
* Remove MAVEN-GroupID from Sub-Projects
* Remove MAVEN-Default-Plugin-Group (org.apache.maven.plugins)

## 1.3.11-SNAPSHOT / 2022-03-11

* Clone of <https://github.com/apache/struts1.git> - STRUTS_1_3_BRANCH
* Add CHANGELOG
* Add LICENSE
* Add mvn_hints.md
* Add NOTICE
* Add README.md
