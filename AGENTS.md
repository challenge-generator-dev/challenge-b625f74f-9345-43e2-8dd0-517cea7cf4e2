# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Manejo de Errores y Reintentos en un Sistema de Pagos**.

| | |
|---|---|
| Tema | E2E technologiesWork 202610091709 |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Quarkus 3.12 |
| Patron arquitectonico | hexagonal/clean con manejo reactivo de errores y reintentos |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- io.quarkus:quarkus-universe-bom 3.12.2
- io.quarkus:quarkus-resteasy-reactive-jackson n/a
- io.quarkus:quarkus-mongodb-panache-reactive n/a
- io.quarkus:quarkus-smallrye-reactive-messaging n/a
- io.quarkus:quarkus-smallrye-health n/a
- io.quarkus:quarkus-smallrye-openapi n/a
- io.github.resilience4j:resilience4j-quarkus 3.0.2
- io.smallrye.reactive:mutiny n/a
- org.junit.jupiter:junit-jupiter n/a
- io.quarkus:quarkus-junit5 n/a
- io.quarkus:quarkus-test-mongodb n/a

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Implementación del Servicio de Pagos**: Servicio de pagos básico que acepta y procesa solicitudes de pago.
- **Fase 2 — Manejo de Errores y Reintentos**: Servicio de pagos mejorado que maneja errores y reintentos de manera eficiente.
- **Fase 3 — Optimización y Refactorización**: Servicio de pagos optimizado y refactorizado que cumple con los requisitos de rendimiento y latencia.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/financial/payments/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (58)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `com.financial.payments.infrastructure.exceptions.PaymentGatewayServiceException`
      El import com.financial.payments.infrastructure.exceptions.PaymentGatewayServiceException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/financial/payments/Application.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentRepository.java` — `org.bson`
      El import org.bson.Document pertenece a org.bson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentRepository.java` — `org.bson.types`
      El import org.bson.types.ObjectId pertenece a org.bson.types, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java` — `org.eclipse.microprofile`
      El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/RiskBureauAdapter.java` — `org.eclipse.microprofile`
      El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/RiskBureauAdapter.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `org.eclipse.microprofile`
      El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `org.eclipse.microprofile`
      El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/config/ResilienceConfig.java` — `io.smallrye.config`
      El import io.smallrye.config.ConfigMapping pertenece a io.smallrye.config, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/exceptions/GlobalExceptionHandler.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `org.eclipse.microprofile`
      El import org.eclipse.microprofile.reactive.messaging.Channel pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java` — `Payment.accountOrigin`
      Se invoca `accountOrigin` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java` — `Payment.accountDestination`
      Se invoca `accountDestination` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java` — `Payment.accountOrigin`
      Se invoca `accountOrigin` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `Payment.destinationAccount`
      Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `CoreBankingAccount.status`
      Se invoca `status` sobre `CoreBankingAccount`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `CoreBankingAccount.balance`
      Se invoca `balance` sobre `CoreBankingAccount`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `Payment.currency`
      Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `GatewayTransaction.amount`
      Se invoca `amount` sobre `GatewayTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `GatewayTransaction.status`
      Se invoca `status` sobre `GatewayTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `GatewayTransaction.timestamp`
      Se invoca `timestamp` sobre `GatewayTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/exceptions/AntiFraudServiceException.java` — `ErrorCode.getHttpStatus`
      Se invoca `getHttpStatus` sobre `ErrorCode`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/exceptions/RiskBureauServiceException.java` — `ErrorCode.getDefaultRetrySeconds`
      Se invoca `getDefaultRetrySeconds` sobre `ErrorCode`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/exceptions/RiskBureauServiceException.java` — `ErrorCode.getHttpStatus`
      Se invoca `getHttpStatus` sobre `ErrorCode`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/exceptions/CoreBankingServiceException.java` — `ErrorCode.getDefaultRetryMillis`
      Se invoca `getDefaultRetryMillis` sobre `ErrorCode`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/exceptions/CoreBankingServiceException.java` — `ErrorCode.getHttpStatus`
      Se invoca `getHttpStatus` sobre `ErrorCode`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/exceptions/GlobalExceptionHandler.java` — `RiskBureauServiceException.getMessage`
      Se invoca `getMessage` sobre `RiskBureauServiceException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/exceptions/GlobalExceptionHandler.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.accountNumber`
      Se invoca `accountNumber` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.currency`
      Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.description`
      Se invoca `description` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.recipientAccount`
      Se invoca `recipientAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.recipientName`
      Se invoca `recipientName` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `ProcessPaymentUseCase.execute`
      Se invoca `execute` sobre `ProcessPaymentUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `CoreBankingPort.reserveFunds`
      Se invoca `reserveFunds` sobre `CoreBankingPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `PaymentGatewayPort.executePayment`
      Se invoca `executePayment` sobre `PaymentGatewayPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `ProcessPaymentUseCase.execute`
      Se invoca `execute` sobre `ProcessPaymentUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `Payment.status`
      Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `Payment.sourceAccount`
      Se invoca `sourceAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `Payment.destinationAccount`
      Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `pom.xml` — `io.github.resilience4j:resilience4j-quarkus@3.0.2`
      io.github.resilience4j:resilience4j-quarkus declara la version 3.0.2, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.

### Presentes (22)

- `pom.xml`
- `src/main/java/com/financial/payments/Application.java`
- `src/main/resources/application.properties`
- `src/main/java/com/financial/payments/domain/model/Payment.java`
- `src/main/java/com/financial/payments/domain/ports/AntiFraudPort.java`
- `src/main/java/com/financial/payments/domain/ports/RiskBureauPort.java`
- `src/main/java/com/financial/payments/domain/ports/CoreBankingPort.java`
- `src/main/java/com/financial/payments/domain/ports/PaymentGatewayPort.java`
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentRepository.java`
- `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java`
- `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java`
- `src/main/java/com/financial/payments/infrastructure/adapters/RiskBureauAdapter.java`
- `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java`
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java`
- `src/main/java/com/financial/payments/infrastructure/config/ResilienceConfig.java`
- `src/main/java/com/financial/payments/infrastructure/exceptions/AntiFraudServiceException.java`
- `src/main/java/com/financial/payments/infrastructure/exceptions/RiskBureauServiceException.java`
- `src/main/java/com/financial/payments/infrastructure/exceptions/CoreBankingServiceException.java`
- `src/main/java/com/financial/payments/infrastructure/exceptions/GlobalExceptionHandler.java`
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java`
- `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java`
- `src/test/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapterTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/financial/payments`
- `src/main/java/com/financial/payments/domain`
- `src/main/java/com/financial/payments/domain/model`
- `src/main/java/com/financial/payments/domain/ports`
- `src/main/java/com/financial/payments/application`
- `src/main/java/com/financial/payments/application/usecases`
- `src/main/java/com/financial/payments/infrastructure`
- `src/main/java/com/financial/payments/infrastructure/adapters`
- `src/main/java/com/financial/payments/infrastructure/config`
- `src/main/java/com/financial/payments/infrastructure/exceptions`
- `src/main/resources`
- `src/test/java/com/financial/payments`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con manejo reactivo de errores y reintentos**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2
- Brecha que el reto ataca: Comprueba manejo de errores y reintentos.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
