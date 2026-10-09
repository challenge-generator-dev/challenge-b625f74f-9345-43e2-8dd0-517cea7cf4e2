# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/financial/payments/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `com.financial.payments.infrastructure.exceptions.PaymentGatewayServiceException`: El import com.financial.payments.infrastructure.exceptions.PaymentGatewayServiceException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/financial/payments/Application.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentRepository.java` — `org.bson`: El import org.bson.Document pertenece a org.bson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentRepository.java` — `org.bson.types`: El import org.bson.types.ObjectId pertenece a org.bson.types, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java` — `org.eclipse.microprofile`: El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/adapters/RiskBureauAdapter.java` — `org.eclipse.microprofile`: El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/adapters/RiskBureauAdapter.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `org.eclipse.microprofile`: El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `org.eclipse.microprofile`: El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/config/ResilienceConfig.java` — `io.smallrye.config`: El import io.smallrye.config.ConfigMapping pertenece a io.smallrye.config, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/exceptions/GlobalExceptionHandler.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `org.eclipse.microprofile`: El import org.eclipse.microprofile.reactive.messaging.Channel pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java` — `Payment.accountOrigin`: Se invoca `accountOrigin` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java` — `Payment.accountDestination`: Se invoca `accountDestination` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java` — `Payment.accountOrigin`: Se invoca `accountOrigin` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `Payment.destinationAccount`: Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `CoreBankingAccount.status`: Se invoca `status` sobre `CoreBankingAccount`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java` — `CoreBankingAccount.balance`: Se invoca `balance` sobre `CoreBankingAccount`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `Payment.currency`: Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `GatewayTransaction.amount`: Se invoca `amount` sobre `GatewayTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `GatewayTransaction.status`: Se invoca `status` sobre `GatewayTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java` — `GatewayTransaction.timestamp`: Se invoca `timestamp` sobre `GatewayTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/exceptions/AntiFraudServiceException.java` — `ErrorCode.getHttpStatus`: Se invoca `getHttpStatus` sobre `ErrorCode`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/exceptions/RiskBureauServiceException.java` — `ErrorCode.getDefaultRetrySeconds`: Se invoca `getDefaultRetrySeconds` sobre `ErrorCode`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/exceptions/RiskBureauServiceException.java` — `ErrorCode.getHttpStatus`: Se invoca `getHttpStatus` sobre `ErrorCode`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/exceptions/CoreBankingServiceException.java` — `ErrorCode.getDefaultRetryMillis`: Se invoca `getDefaultRetryMillis` sobre `ErrorCode`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/exceptions/CoreBankingServiceException.java` — `ErrorCode.getHttpStatus`: Se invoca `getHttpStatus` sobre `ErrorCode`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/exceptions/GlobalExceptionHandler.java` — `RiskBureauServiceException.getMessage`: Se invoca `getMessage` sobre `RiskBureauServiceException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/exceptions/GlobalExceptionHandler.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.accountNumber`: Se invoca `accountNumber` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.currency`: Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.description`: Se invoca `description` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.recipientAccount`: Se invoca `recipientAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `PaymentRequest.recipientName`: Se invoca `recipientName` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java` — `ProcessPaymentUseCase.execute`: Se invoca `execute` sobre `ProcessPaymentUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `CoreBankingPort.reserveFunds`: Se invoca `reserveFunds` sobre `CoreBankingPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `PaymentGatewayPort.executePayment`: Se invoca `executePayment` sobre `PaymentGatewayPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `ProcessPaymentUseCase.execute`: Se invoca `execute` sobre `ProcessPaymentUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `Payment.status`: Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `Payment.sourceAccount`: Se invoca `sourceAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `Payment.destinationAccount`: Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `pom.xml` — `io.github.resilience4j:resilience4j-quarkus@3.0.2`: io.github.resilience4j:resilience4j-quarkus declara la version 3.0.2, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2

### Brecha de conocimiento
Comprueba manejo de errores y reintentos.

### Reto
- Tema: E2E technologiesWork 202610091709
- Seniority: senior-l2
- Tipo: practical
- Título: Manejo de Errores y Reintentos en un Sistema de Pagos
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Implementación del Servicio de Pagos — objetivo: Crear un servicio que acepte solicitudes de pago y las procese correctamente. — entregable (NO resolver): Servicio de pagos básico que acepta y procesa solicitudes de pago.
- Fase 2: Manejo de Errores y Reintentos — objetivo: Mejorar el servicio para manejar errores y reintentos de manera eficiente. — entregable (NO resolver): Servicio de pagos mejorado que maneja errores y reintentos de manera eficiente.
- Fase 3: Optimización y Refactorización — objetivo: Optimizar y refactorizar el servicio para mejorar su rendimiento y mantenibilidad. — entregable (NO resolver): Servicio de pagos optimizado y refactorizado que cumple con los requisitos de rendimiento y latencia.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.financial.payments</groupId>
    <artifactId>payment-processing-service</artifactId>
    <version>1.0.0</version>
    <properties>
        <compiler-plugin.version>3.13.0</compiler-plugin.version>
        <maven.compiler.parameters>true</maven.compiler.parameters>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <quarkus.platform.artifact-id>quarkus-universe-bom</quarkus.platform.artifact-id>
        <quarkus.platform.group-id>io.quarkus.platform</quarkus.platform.group-id>
        <quarkus.platform.version>3.12.2</quarkus.platform.version>
        <resilience4j.version>3.0.2</resilience4j.version>
    </properties>

    <dependencyManagement>
        <dependencies>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.14.2</version>
        </dependency>

            <dependency>
                <groupId>${quarkus.platform.group-id}</groupId>
                <artifactId>${quarkus.platform.artifact-id}</artifactId>
                <version>${quarkus.platform.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <!-- Quarkus Core -->
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-resteasy-reactive-jackson</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-mongodb-panache-reactive</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-smallrye-reactive-messaging</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-smallrye-health</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-smallrye-openapi</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-quarkus</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Mutiny -->
        <dependency>
            <groupId>io.smallrye.reactive</groupId>
            <artifactId>mutiny</artifactId>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-junit5</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-test-mongodb</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>${quarkus.platform.group-id}</groupId>
                <artifactId>quarkus-maven-plugin</artifactId>
                <version>${quarkus.platform.version}</version>
                <extensions>true</extensions>
                <executions>
                    <execution>
                        <goals>
                            <goal>build</goal>
                            <goal>generate-code</goal>
                            <goal>generate-code-tests</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${compiler-plugin.version}</version>
                <configuration>
                    <parameters>${maven.compiler.parameters}</parameters>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <profiles>
        <profile>
            <id>native</id>
            <activation>
                <property>
                    <name>native</name>
                </property>
            </activation>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-failsafe-plugin</artifactId>
                        <version>3.3.0</version>
                        <executions>
                            <execution>
                                <goals>
                                    <goal>integration-test</goal>
                                    <goal>verify</goal>
                                </goals>
                                <configuration>
                                    <systemPropertyVariables>
                                        <native.image.path>${project.build.directory}/${project.build.finalName}-runner</native.image.path>
                                        <java.util.logging.manager>org.jboss.logmanager.LogManager</java.util.logging.manager>
                                        <maven.home>${maven.home}</maven.home>
                                    </systemPropertyVariables>
                                </configuration>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
</project>

// === ARCHIVO: src/main/java/com/financial/payments/Application.java ===
package com.financial.payments;


import com.financial.payments.domain.model.Payment;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@QuarkusMain
public class Application implements QuarkusApplication {

    private static final Logger LOGGER = Logger.getLogger(Application.class);

    @Override
    public int run(String... args) {
        LOGGER.info("Payment Processing Service is starting...");
        Quarkus.waitForExit();
        return 0;
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Payment Processing Service started successfully.");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("Payment Processing Service is stopping...");
    }
}

// === ARCHIVO: src/main/resources/application.properties ===
# Quarkus Configuration
quarkus.application.name=payment-processing-service
quarkus.application.version=1.0.0

# HTTP Configuration
quarkus.http.port=8080
quarkus.http.host=0.0.0.0
quarkus.http.io-threads=20
quarkus.http.limits.max-body-size=10M

# MongoDB Reactive Configuration
quarkus.mongodb.connection-string=mongodb://localhost:27017
quarkus.mongodb.database=payments_db
quarkus.mongodb.max-pool-size=20
quarkus.mongodb.min-pool-size=5
quarkus.mongodb.max-connection-idle-time=PT10M

# Resilience4j Configuration
## Circuit Breaker
quarkus.resilience4j.circuitbreaker.instances.antiFraudService.min-number-of-calls=10
quarkus.resilience4j.circuitbreaker.instances.antiFraudService.failure-rate-threshold=50
quarkus.resilience4j.circuitbreaker.instances.antiFraudService.wait-duration-in-open-state=PT5S
quarkus.resilience4j.circuitbreaker.instances.antiFraudService.permitted-number-of-calls-in-half-open-state=3
quarkus.resilience4j.circuitbreaker.instances.antiFraudService.sliding-window-type=COUNT_BASED
quarkus.resilience4j.circuitbreaker.instances.antiFraudService.sliding-window-size=10

quarkus.resilience4j.circuitbreaker.instances.riskBureauService.min-number-of-calls=10
quarkus.resilience4j.circuitbreaker.instances.riskBureauService.failure-rate-threshold=50
quarkus.resilience4j.circuitbreaker.instances.riskBureauService.wait-duration-in-open-state=PT5S
quarkus.resilience4j.circuitbreaker.instances.riskBureauService.permitted-number-of-calls-in-half-open-state=3
quarkus.resilience4j.circuitbreaker.instances.riskBureauService.sliding-window-type=COUNT_BASED
quarkus.resilience4j.circuitbreaker.instances.riskBureauService.sliding-window-size=10

quarkus.resilience4j.circuitbreaker.instances.coreBankingService.min-number-of-calls=10
quarkus.resilience4j.circuitbreaker.instances.coreBankingService.failure-rate-threshold=50
quarkus.resilience4j.circuitbreaker.instances.coreBankingService.wait-duration-in-open-state=PT5S
quarkus.resilience4j.circuitbreaker.instances.coreBankingService.permitted-number-of-calls-in-half-open-state=3
quarkus.resilience4j.circuitbreaker.instances.coreBankingService.sliding-window-type=COUNT_BASED
quarkus.resilience4j.circuitbreaker.instances.coreBankingService.sliding-window-size=10

quarkus.resilience4j.circuitbreaker.instances.paymentGatewayService.min-number-of-calls=10
quarkus.resilience4j.circuitbreaker.instances.paymentGatewayService.failure-rate-threshold=50
quarkus.resilience4j.circuitbreaker.instances.paymentGatewayService.wait-duration-in-open-state=PT5S
quarkus.resilience4j.circuitbreaker.instances.paymentGatewayService.permitted-number-of-calls-in-half-open-state=3
quarkus.resilience4j.circuitbreaker.instances.paymentGatewayService.sliding-window-type=COUNT_BASED
quarkus.resilience4j.circuitbreaker.instances.paymentGatewayService.sliding-window-size=10

## Retry
quarkus.resilience4j.retry.instances.antiFraudService.max-attempts=3
quarkus.resilience4j.retry.instances.antiFraudService.wait-duration=PT1S
quarkus.resilience4j.retry.instances.antiFraudService.retry-exceptions=com.financial.payments.infrastructure.exceptions.AntiFraudServiceException,io.smallrye.mutiny.CompositeException

quarkus.resilience4j.retry.instances.riskBureauService.max-attempts=3
quarkus.resilience4j.retry.instances.riskBureauService.wait-duration=PT1S
quarkus.resilience4j.retry.instances.riskBureauService.retry-exceptions=com.financial.payments.infrastructure.exceptions.RiskBureauServiceException,io.smallrye.mutiny.CompositeException

quarkus.resilience4j.retry.instances.coreBankingService.max-attempts=3
quarkus.resilience4j.retry.instances.coreBankingService.wait-duration=PT1S
quarkus.resilience4j.retry.instances.coreBankingService.retry-exceptions=com.financial.payments.infrastructure.exceptions.CoreBankingServiceException,io.smallrye.mutiny.CompositeException

quarkus.resilience4j.retry.instances.paymentGatewayService.max-attempts=3
quarkus.resilience4j.retry.instances.paymentGatewayService.wait-duration=PT1S
quarkus.resilience4j.retry.instances.paymentGatewayService.retry-exceptions=java.lang.Exception,io.smallrye.mutiny.CompositeException

## Rate Limiter
quarkus.resilience4j.ratelimiter.instances.paymentService.limit-for-period=1500
quarkus.resilience4j.ratelimiter.instances.paymentService.limit-refresh-period=PT1S
quarkus.resilience4j.ratelimiter.instances.paymentService.timeout-duration=PT0.1S

# Reactive Messaging Configuration
quarkus.smallrye-reactive-messaging.enabled=true
mp.messaging.incoming.payment-requests.connector=smallrye-kafka
mp.messaging.incoming.payment-requests.topic=payment-requests
mp.messaging.incoming.payment-requests.group.id=payment-processing-group
mp.messaging.incoming.payment-requests.auto.offset.reset=earliest
mp.messaging.incoming.payment-requests.bootstrap.servers=localhost:9092

mp.messaging.outgoing.payment-confirmations.connector=smallrye-kafka
mp.messaging.outgoing.payment-confirmations.topic=payment-confirmations
mp.messaging.outgoing.payment-confirmations.bootstrap.servers=localhost:9092

# Health Checks
quarkus.smallrye-health.root-path=/health
quarkus.smallrye-health.liveness-path=/health/live
quarkus.smallrye-health.readiness-path=/health/ready

# OpenAPI
quarkus.smallrye-openapi.path=/openapi
quarkus.smallrye-openapi.info-title=Payment Processing Service API
quarkus.smallrye-openapi.info-version=1.0.0
quarkus.smallrye-openapi.info-description=API para el procesamiento de pagos con manejo de errores y reintentos

// === ARCHIVO: src/main/java/com/financial/payments/domain/model/Payment.java ===
package com.financial.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad de dominio que representa una solicitud de pago.
 * Contiene los atributos esenciales del pago y su estado actual.
 */
public record Payment(
    UUID id,
    String sourceAccount,
    String destinationAccount,
    BigDecimal amount,
    PaymentStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String transactionReference,
    int retryCount,
    String failureReason) {

    /**
     * Constructor principal que inicializa un pago con valores predeterminados.
     * @param sourceAccount Cuenta origen del pago
     * @param destinationAccount Cuenta destino del pago
     * @param amount Monto a transferir
     */
    public Payment {
        if (sourceAccount == null || sourceAccount.isBlank()) {
            throw new IllegalArgumentException("La cuenta origen no puede ser nula o vacía");
        }
        if (destinationAccount == null || destinationAccount.isBlank()) {
            throw new IllegalArgumentException("La cuenta destino no puede ser nula o vacía");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (status == null) {
            status = PaymentStatus.PENDING;
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (transactionReference == null) {
            transactionReference = generateTransactionReference();
        }
        retryCount = 0;
        failureReason = null;
    }

    /**
     * Genera una referencia única para la transacción.
     * @return Referencia de transacción con formato UUID
     */
    private static String generateTransactionReference() {
        return "TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Crea una nueva instancia de Payment con estado actualizado.
     * @param newStatus Nuevo estado del pago
     * @return Nueva instancia de Payment con el estado actualizado
     */
    public Payment withStatus(PaymentStatus newStatus) {
        return new Payment(
            this.id,
            this.sourceAccount,
            this.destinationAccount,
            this.amount,
            newStatus,
            this.createdAt,
            LocalDateTime.now(),
            this.transactionReference,
            this.retryCount,
            this.failureReason
        );
    }

    /**
     * Crea una nueva instancia de Payment con conteo de reintentos incrementado.
     * @param reason Razón del fallo
     * @return Nueva instancia de Payment con retryCount incrementado
     */
    public Payment withIncrementedRetry(String reason) {
        return new Payment(
            this.id,
            this.sourceAccount,
            this.destinationAccount,
            this.amount,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.transactionReference,
            this.retryCount + 1,
            reason
        );
    }

    /**
     * Enum que representa los posibles estados de un pago.
     */
    public enum PaymentStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        REJECTED,
        ROLLED_BACK
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/domain/ports/AntiFraudPort.java ===
package com.financial.payments.domain.ports;

import com.financial.payments.domain.model.Payment;
import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el motor antifraude.
 * Define el contrato que debe cumplir cualquier implementación de antifraude.
 */
public interface AntiFraudPort {

    /**
     * Verifica el riesgo de fraude de un pago.
     * @param payment Pago a verificar
     * @return Uni que emite true si el pago es seguro, false si se detecta fraude
     */
    Uni<Boolean> verifyFraudRisk(Payment payment);

    /**
     * Obtiene el puntaje de riesgo asociado a un pago.
     * @param payment Pago a evaluar
     * @return Uni que emite el puntaje de riesgo (0-100)
     */
    Uni<Integer> getRiskScore(Payment payment);
}

// === ARCHIVO: src/main/java/com/financial/payments/domain/ports/RiskBureauPort.java ===
package com.financial.payments.domain.ports;

import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el buró de riesgos.
 * Define el contrato para consultar información de riesgo de cuentas.
 */
public interface RiskBureauPort {

    /**
     * Consulta el nivel de riesgo de una cuenta en el buró.
     * @param accountNumber Número de cuenta a consultar
     * @return Uni que emite el nivel de riesgo (LOW, MEDIUM, HIGH)
     */
    Uni<RiskLevel> getAccountRiskLevel(String accountNumber);

    /**
     * Consulta si una cuenta tiene restricciones en el buró.
     * @param accountNumber Número de cuenta a verificar
     * @return Uni que emite true si la cuenta tiene restricciones, false en caso contrario
     */
    Uni<Boolean> hasAccountRestrictions(String accountNumber);

    /**
     * Enum que representa los niveles de riesgo posibles.
     */
    enum RiskLevel {
        LOW,
        MEDIUM,
        HIGH
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/domain/ports/CoreBankingPort.java ===
package com.financial.payments.domain.ports;

import com.financial.payments.domain.model.Payment;
import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el core bancario.
 * Define las operaciones de consulta y modificación en el sistema bancario central.
 */
public interface CoreBankingPort {

    /**
     * Verifica si la cuenta de origen tiene fondos suficientes para el pago.
     * @param accountNumber Número de cuenta del originador
     * @param amount Monto a debitar
     * @return Uni con true si hay fondos suficientes, false en caso contrario
     */
    Uni<Boolean> verifySufficientFunds(String accountNumber, java.math.BigDecimal amount);

    /**
     * Debita el monto de la cuenta del originador.
     * @param payment Información del pago a debitar
     * @return Uni con el identificador de la transacción bancaria
     */
    Uni<String> debitAccount(Payment payment);

    /**
     * Revierte un débito previamente realizado.
     * @param transactionId Identificador de la transacción a revertir
     * @return Uni con true si la reversión fue exitosa
     */
    Uni<Boolean> reverseDebit(String transactionId);

    /**
     * Consulta el saldo actual de una cuenta.
     * @param accountNumber Número de cuenta a consultar
     * @return Uni con el saldo actual
     */
    Uni<java.math.BigDecimal> getAccountBalance(String accountNumber);

    /**
     * Verifica que la cuenta esté activa y habilitada para operaciones.
     * @param accountNumber Número de cuenta a verificar
     * @return Uni con true si la cuenta está activa
     */
    Uni<Boolean> isAccountActive(String accountNumber);

    /**
     * Registra la transacción de crédito en la cuenta del beneficiario.
     * @param payment Información del pago a acreditar
     * @return Uni con el identificador de la transacción de crédito
     */
    Uni<String> creditAccount(Payment payment);
}

// === ARCHIVO: src/main/java/com/financial/payments/domain/ports/PaymentGatewayPort.java ===
package com.financial.payments.domain.ports;

import com.financial.payments.domain.model.Payment;
import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el gateway de pagos externo.
 * Maneja la comunicación con proveedores de pago para procesar transacciones.
 */
public interface PaymentGatewayPort {

    /**
     * Procesa un pago a través del gateway externo.
     * @param payment Información completa del pago
     * @return Uni con el resultado del procesamiento
     */
    Uni<PaymentGatewayResult> processPayment(Payment payment);

    /**
     * Consulta el estado de una transacción previamente procesada.
     * @param transactionReference Referencia de la transacción en el gateway
     * @return Uni con el estado actual de la transacción
     */
    Uni<PaymentGatewayStatus> getTransactionStatus(String transactionReference);

    /**
     * Cancela una transacción pendiente en el gateway.
     * @param transactionReference Referencia de la transacción a cancelar
     * @return Uni con true si la cancelación fue exitosa
     */
    Uni<Boolean> cancelTransaction(String transactionReference);

    /**
     * Reembolsa una transacción previamente procesada.
     * @param transactionReference Referencia de la transacción a reembolsar
     * @param amount Monto a reembolsar
     * @return Uni con true si el reembolso fue exitoso
     */
    Uni<Boolean> refundTransaction(String transactionReference, java.math.BigDecimal amount);

    /**
     * Resultado del procesamiento de un pago en el gateway.
     */
    record PaymentGatewayResult(
        String transactionId,
        PaymentGatewayStatus status,
        String authorizationCode,
        String responseMessage,
        java.time.Instant processedAt
    ) {}

    /**
     * Estados posibles de una transacción en el gateway de pagos.
     */
    enum PaymentGatewayStatus {
        PENDING,
        AUTHORIZED,
        CAPTURED,
        DECLINED,
        CANCELLED,
        REFUNDED,
        FAILED
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/adapters/PaymentRepository.java ===
package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.model.Payment.PaymentStatus;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio reactivo para persistir solicitudes de pago en MongoDB.
 * Utiliza Panache Reactive para operaciones asíncronas con la base de datos.
 */
@ApplicationScoped
public class PaymentRepository implements PanacheMongoRepositoryBase<Payment, ObjectId> {

    @Inject
    ReactiveMongoClient mongoClient;

    /**
     * Guarda una nueva solicitud de pago en la colección.
     * @param payment Entidad de pago a persistir
     * @return Uni con el payment persistido con su ID asignado
     */
    public Uni<Payment> save(Payment payment) {
        return persist(payment)
            .replaceWith(payment);
    }

    /**
     * Busca un pago por su referencia única de transacción.
     * @param transactionReference Referencia única del pago
     * @return Uni con el payment encontrado o vacío
     */
    public Uni<Optional<Payment>> findByTransactionReference(String transactionReference) {
        return find("transactionReference", transactionReference)
            .firstResultOptional();
    }

    /**
     * Busca pagos por el número de cuenta del originador.
     * @param accountNumber Número de cuenta del originador
     * @return Uni con lista de pagos asociados a la cuenta
     */
    public Uni<List<Payment>> findByOriginatorAccount(String accountNumber) {
        return find("originatorAccountNumber", accountNumber)
            .list();
    }

    /**
     * Busca pagos por el número de cuenta del beneficiario.
     * @param accountNumber Número de cuenta del beneficiario
     * @return Uni con lista de pagos recibidos
     */
    public Uni<List<Payment>> findByBeneficiaryAccount(String accountNumber) {
        return find("beneficiaryAccountNumber", accountNumber)
            .list();
    }

    /**
     * Busca pagos por su estado actual.
     * @param status Estado del pago a buscar
     * @return Uni con lista de pagos en el estado especificado
     */
    public Uni<List<Payment>> findByStatus(PaymentStatus status) {
        return find("status", status)
            .list();
    }

    /**
     * Busca pagos creados dentro de un rango de tiempo.
     * @param startTime Timestamp de inicio del rango
     * @param endTime Timestamp de fin del rango
     * @return Uni con lista de pagos en el rango de tiempo
     */
    public Uni<List<Payment>> findByCreatedAtBetween(Instant startTime, Instant endTime) {
        return find("createdAt >= ?1 and createdAt <= ?2", startTime, endTime)
            .list();
    }

    /**
     * Actualiza el estado de un pago existente.
     * @param transactionReference Referencia del pago a actualizar
     * @param newStatus Nuevo estado del pago
     * @return Uni con el número de documentos modificados
     */
    public Uni<Long> updateStatus(String transactionReference, PaymentStatus newStatus) {
        return update("status = ?1 where transactionReference = ?2", newStatus, transactionReference);
    }

    /**
     * Actualiza el estado y añade información de失败 del gateway.
     * @param transactionReference Referencia del pago
     * @param newStatus Nuevo estado
     * @param failureReason Razón del fallo
     * @param gatewayTransactionId ID de la transacción en el gateway
     * @return Uni con el número de documentos modificados
     */
    public Uni<Long> updateStatusWithFailure(
            String transactionReference,
            PaymentStatus newStatus,
            String failureReason,
            String gatewayTransactionId) {
        return update(
            "status = ?1, failureReason = ?2, gatewayTransactionId = ?3, updatedAt = ?4 where transactionReference = ?5",
            newStatus, failureReason, gatewayTransactionId, Instant.now(), transactionReference
        );
    }

    /**
     * Incrementa el contador de reintentos de un pago.
     * @param transactionReference Referencia del pago
     * @param newRetryCount Nuevo conteo de reintentos
     * @param lastFailureReason Razón del último fallo
     * @return Uni con el número de documentos modificados
     */
    public Uni<Long> incrementRetryCount(
            String transactionReference,
            int newRetryCount,
            String lastFailureReason) {
        return update(
            "retryCount = ?1, failureReason = ?2, updatedAt = ?3 where transactionReference = ?4",
            newRetryCount, lastFailureReason, Instant.now(), transactionReference
        );
    }

    /**
     * Cuenta la cantidad de pagos en un estado específico.
     * @param status Estado a contar
     * @return Uni con la cantidad de pagos en ese estado
     */
    public Uni<Long> countByStatus(PaymentStatus status) {
        return count("status", status);
    }

    /**
     * Obtiene pagos pendientes de procesamiento que han excedido el tiempo máximo.
     * @param maxAgeMinutes Edad máxima en minutos para considerar un pago como estancado
     * @return Uni con lista de pagos estancados
     */
    public Uni<List<Payment>> findStalePendingPayments(int maxAgeMinutes) {
        Instant cutoffTime = Instant.now().minusSeconds(maxAgeMinutes * 60L);
        return find("status = ?1 and createdAt < ?2", PaymentStatus.PENDING, cutoffTime)
            .list();
    }

    /**
     * Elimina un pago por su referencia (para casos de prueba o limpieza).
     * @param transactionReference Referencia del pago a eliminar
     * @return Uni con true si se eliminó correctamente
     */
    public Uni<Boolean> deleteByTransactionReference(String transactionReference) {
        return delete("transactionReference", transactionReference)
            .map(deletedCount -> deletedCount > 0);
    }

    /**
     * Verifica si existe un pago con la referencia dada.
     * @param transactionReference Referencia a verificar
     * @return Uni con true si existe
     */
    public Uni<Boolean> existsByTransactionReference(String transactionReference) {
        return count("transactionReference", transactionReference)
            .map(count -> count > 0);
    }

    /**
     * Obtiene el historial de pagos para auditoría.
     * @param accountNumber Número de cuenta (originador o beneficiario)
     * @param limit Cantidad máxima de registros a retornar
     * @return Uni con lista de pagos ordenados por fecha descendente
     */
    public Uni<List<Payment>> getPaymentHistory(String accountNumber, int limit) {
        return find("originatorAccountNumber = ?1 or beneficiaryAccountNumber = ?2", accountNumber, accountNumber)
            .sort("-createdAt")
            .page(0, limit)
            .list();
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/application/usecases/ProcessPaymentUseCase.java ===
package com.financial.payments.application.usecases;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.model.Payment.PaymentStatus;
import com.financial.payments.domain.ports.AntiFraudPort;
import com.financial.payments.domain.ports.RiskBureauPort;
import com.financial.payments.domain.ports.RiskBureauPort.RiskLevel;
import com.financial.payments.infrastructure.adapters.PaymentRepository;
import com.financial.payments.infrastructure.exceptions.AntiFraudServiceException;
import com.financial.payments.infrastructure.exceptions.RiskBureauServiceException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ProcessPaymentUseCase {

    private static final Logger LOG = Logger.getLogger(ProcessPaymentUseCase.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final String CIRCUIT_BREAKER_NAME = "paymentCircuitBreaker";

    private final AntiFraudPort antiFraudPort;
    private final RiskBureauPort riskBureauPort;
    private final PaymentRepository paymentRepository;

    @Inject
    public ProcessPaymentUseCase(
            AntiFraudPort antiFraudPort,
            RiskBureauPort riskBureauPort,
            PaymentRepository paymentRepository) {
        this.antiFraudPort = antiFraudPort;
        this.riskBureauPort = riskBureauPort;
        this.paymentRepository = paymentRepository;
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "processPaymentFallback")
    @Retry(name = "paymentRetry", maxAttempts = MAX_RETRY_ATTEMPTS)
    public Uni<Payment> processPayment(Payment payment) {
        LOG.infof("Iniciando procesamiento de pago: %s", payment.transactionReference());
        
        return validatePayment(payment)
                .onFailure().recoverWithUni(this::handleValidationFailure)
                .chain(this::checkAntiFraud)
                .onFailure(this::isRecoverableFraudError).recoverWithUni(this::handleAntiFraudFailure)
                .chain(this::checkRiskBureau)
                .onFailure(this::isRecoverableRiskError).recoverWithUni(this::handleRiskBureauFailure)
                .chain(this::savePayment)
                .onItem().invoke(p -> LOG.infof("Pago procesado exitosamente: %s", p.transactionReference()));
    }

    private Uni<Payment> validatePayment(Payment payment) {
        if (payment == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Payment no puede ser nulo"));
        }
        if (payment.accountOrigin() == null || payment.accountOrigin().isBlank()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Cuenta origen es requerida"));
        }
        if (payment.accountDestination() == null || payment.accountDestination().isBlank()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Cuenta destino es requerida"));
        }
        if (payment.amount() == null || payment.amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return Uni.createFrom().failure(new IllegalArgumentException("Monto debe ser mayor a cero"));
        }
        return Uni.createFrom().item(payment);
    }

    private Uni<Payment> handleValidationFailure(Throwable failure) {
        LOG.errorf("Validación de pago fallida: %s", failure.getMessage());
        return Uni.createFrom().failure(failure);
    }

    private Uni<Payment> checkAntiFraud(Payment payment) {
        LOG.debugf("Verificando riesgo de fraude para pago: %s", payment.transactionReference());
        return antiFraudPort.verifyFraudRisk(payment)
                .onItem().transformToUni(isFraudulent -> {
                    if (Boolean.TRUE.equals(isFraudulent)) {
                        LOG.warnf("Pago marcado como fraudulento: %s", payment.transactionReference());
                        return Uni.createFrom().failure(
                                new AntiFraudServiceException("Pago rechazado por motor antifraude", payment.transactionReference()));
                    }
                    return Uni.createFrom().item(payment);
                });
    }

    private boolean isRecoverableFraudError(Throwable t) {
        return t instanceof AntiFraudServiceException 
                || t instanceof CallNotPermittedException
                || (t.getCause() != null && t.getCause() instanceof java.io.IOException);
    }

    private Uni<Payment> handleAntiFraudFailure(Throwable failure) {
        LOG.errorf("Error en verificación antifraude: %s", failure.getMessage());
        if (failure instanceof AntiFraudServiceException) {
            Payment failedPayment = ((AntiFraudServiceException) failure).getPayment();
            return paymentRepository.save(failedPayment.withStatus(PaymentStatus.REJECTED));
        }
        return Uni.createFrom().failure(failure);
    }

    private Uni<Payment> checkRiskBureau(Payment payment) {
        LOG.debugf("Consultando buró de riesgos para cuenta: %s", payment.accountOrigin());
        return riskBureauPort.getAccountRiskLevel(payment.accountOrigin())
                .onItem().transformToUni(riskLevel -> {
                    if (riskLevel == RiskLevel.HIGH) {
                        LOG.warnf("Cuenta con riesgo alto: %s", payment.accountOrigin());
                        return Uni.createFrom().failure(
                                new RiskBureauServiceException("Cuenta con nivel de riesgo alto", payment.accountOrigin()));
                    }
                    return checkAccountRestrictions(payment);
                });
    }

    private Uni<Payment> checkAccountRestrictions(Payment payment) {
        return riskBureauPort.hasAccountRestrictions(payment.accountOrigin())
                .onItem().transformToUni(hasRestrictions -> {
                    if (Boolean.TRUE.equals(hasRestrictions)) {
                        LOG.warnf("Cuenta con restricciones: %s", payment.accountOrigin());
                        return Uni.createFrom().failure(
                                new RiskBureauServiceException("Cuenta con restricciones activas", payment.accountOrigin()));
                    }
                    return Uni.createFrom().item(payment);
                });
    }

    private boolean isRecoverableRiskError(Throwable t) {
        return t instanceof RiskBureauServiceException
                || t instanceof CallNotPermittedException
                || (t.getCause() != null && t.getCause() instanceof java.io.IOException);
    }

    private Uni<Payment> handleRiskBureauFailure(Throwable failure) {
        LOG.errorf("Error en verificación de buró de riesgos: %s", failure.getMessage());
        if (failure instanceof RiskBureauServiceException) {
            Payment failedPayment = ((RiskBureauServiceException) failure).getAccountNumber();
            return paymentRepository.save(failedPayment.withStatus(PaymentStatus.REJECTED));
        }
        return Uni.createFrom().failure(failure);
    }

    private Uni<Payment> savePayment(Payment payment) {
        LOG.debugf("Guardando pago en repositorio: %s", payment.transactionReference());
        return paymentRepository.save(payment.withStatus(PaymentStatus.PROCESSED));
    }

    private Uni<Payment> processPaymentFallback(Payment payment, Throwable failure) {
        LOG.errorf("Fallback activado para pago %s: %s", payment.transactionReference(), failure.getMessage());
        Payment failedPayment = payment.withIncrementedRetry(failure.getMessage());
        return paymentRepository.save(failedPayment.withStatus(PaymentStatus.PENDING_RETRY))
                .onItem().invoke(p -> LOG.warnf("Pago guardado con estado PENDING_RETRY: %s", p.transactionReference()));
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapter.java ===
package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.ports.AntiFraudPort;
import com.financial.payments.infrastructure.exceptions.AntiFraudServiceException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class AntiFraudAdapter implements AntiFraudPort {

    private static final Logger LOG = Logger.getLogger(AntiFraudAdapter.class);
    private static final String CIRCUIT_BREAKER_NAME = "antiFraudCircuitBreaker";
    private static final String RETRY_NAME = "antiFraudRetry";
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final BigDecimal HIGH_RISK_THRESHOLD = new BigDecimal("50000");
    private static final BigDecimal SUSPICIOUS_AMOUNT = new BigDecimal("10000");

    private final Random random = new Random();
    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();

    @ConfigProperty(name = "antifraud.service.url", defaultValue = "http://localhost:8081")
    String serviceUrl;

    @ConfigProperty(name = "antifraud.service.timeout", defaultValue = "5000")
    long timeoutMs;

    @ConfigProperty(name = "antifraud.service.mock.enabled", defaultValue = "true")
    boolean mockEnabled;

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "verifyFraudRiskFallback")
    @Retry(name = RETRY_NAME, maxAttempts = MAX_RETRY_ATTEMPTS)
    public Uni<Boolean> verifyFraudRisk(Payment payment) {
        LOG.infof("Verificando riesgo de fraude para transacción: %s", payment.transactionReference());
        incrementRequestCount(payment.accountOrigin());

        if (mockEnabled) {
            return mockVerifyFraudRisk(payment);
        }

        return invokeExternalAntiFraudService(payment)
                .onFailure(this::isTransientError).recoverWithUni(this::handleTransientFailure)
                .onFailure().recoverWithItem(this::handlePermanentFailure);
    }

    private Uni<Boolean> mockVerifyFraudRisk(Payment payment) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            
            if (payment.amount().compareTo(HIGH_RISK_THRESHOLD) > 0) {
                LOG.warnf("Monto alto detectado: %s para transacción %s", 
                        payment.amount(), payment.transactionReference());
                return Boolean.TRUE;
            }
            
            if (payment.amount().compareTo(SUSPICIOUS_AMOUNT) > 0 && random.nextInt(10) < 3) {
                LOG.warnf("Monto sospechoso y patrón aleatorio: %s", payment.transactionReference());
                return Boolean.TRUE;
            }
            
            int requestCount = requestCounts.getOrDefault(payment.accountOrigin(), 0);
            if (requestCount > 10) {
                LOG.warnf("Exceso de solicitudes desde cuenta: %s", payment.accountOrigin());
                return Boolean.TRUE;
            }
            
            return Boolean.FALSE;
        });
    }

    private Uni<Boolean> invokeExternalAntiFraudService(Payment payment) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            LOG.debugf("Invocando servicio externo de antifraude: %s", serviceUrl);
            return Boolean.FALSE;
        });
    }

    private void simulateLatency() {
        try {
            Thread.sleep(random.nextInt(100) + 50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isTransientError(Throwable t) {
        return t instanceof java.io.IOException
                || t instanceof java.util.concurrent.TimeoutException
                || (t.getCause() != null && t.getCause() instanceof java.io.IOException);
    }

    private Uni<Boolean> handleTransientFailure(Throwable failure) {
        LOG.warnf("Error transitorio en servicio antifraude, reintentando: %s", failure.getMessage());
        return Uni.createFrom().failure(
                new AntiFraudServiceException("Error transitorio en servicio antifraude", 
                        failure.getMessage(), paymentFromError(failure)));
    }

    private Boolean handlePermanentFailure(Throwable failure) {
        LOG.errorf("Error permanente en servicio antifraude: %s", failure.getMessage());
        return Boolean.FALSE;
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getRiskScoreFallback")
    @Retry(name = RETRY_NAME, maxAttempts = MAX_RETRY_ATTEMPTS)
    public Uni<Integer> getRiskScore(Payment payment) {
        LOG.infof("Obteniendo score de riesgo para transacción: %s", payment.transactionReference());
        
        if (mockEnabled) {
            return mockGetRiskScore(payment);
        }

        return invokeExternalRiskScoreService(payment);
    }

    private Uni<Integer> mockGetRiskScore(Payment payment) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            
            int baseScore = random.nextInt(50);
            
            if (payment.amount().compareTo(HIGH_RISK_THRESHOLD) > 0) {
                baseScore += 40;
            } else if (payment.amount().compareTo(SUSPICIOUS_AMOUNT) > 0) {
                baseScore += 20;
            }
            
            int requestCount = requestCounts.getOrDefault(payment.accountOrigin(), 0);
            if (requestCount > 5) {
                baseScore += Math.min(requestCount * 2, 30);
            }
            
            return Math.min(baseScore, 100);
        });
    }

    private Uni<Integer> invokeExternalRiskScoreService(Payment payment) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            LOG.debugf("Invocando servicio externo de score de riesgo: %s", serviceUrl);
            return 0;
        });
    }

    private void incrementRequestCount(String accountNumber) {
        requestCounts.merge(accountNumber, 1, Integer::sum);
    }

    private Payment paymentFromError(Throwable t) {
        if (t instanceof AntiFraudServiceException) {
            return ((AntiFraudServiceException) t).getPayment();
        }
        return null;
    }

    private Uni<Boolean> verifyFraudRiskFallback(Payment payment, Throwable failure) {
        LOG.errorf("Fallback de circuit breaker activado para verifyFraudRisk: %s", failure.getMessage());
        if (failure instanceof CallNotPermittedException) {
            return Uni.createFrom().failure(
                    new AntiFraudServiceException("Servicio antifraude no disponible temporalmente", 
                            payment.transactionReference()));
        }
        return Uni.createFrom().item(Boolean.FALSE);
    }

    private Uni<Integer> getRiskScoreFallback(Payment payment, Throwable failure) {
        LOG.errorf("Fallback de circuit breaker activado para getRiskScore: %s", failure.getMessage());
        return Uni.createFrom().item(50);
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/adapters/RiskBureauAdapter.java ===
package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.ports.RiskBureauPort;
import com.financial.payments.domain.ports.RiskBureauPort.RiskLevel;
import com.financial.payments.infrastructure.exceptions.RiskBureauServiceException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class RiskBureauAdapter implements RiskBureauPort {

    private static final Logger LOG = Logger.getLogger(RiskBureauAdapter.class);
    private static final String CIRCUIT_BREAKER_NAME = "riskBureauCircuitBreaker";
    private static final String RETRY_NAME = "riskBureauRetry";
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final Set<String> RESTRICTED_ACCOUNTS = Set.of(
            "ACC-RESTRICTED-001",
            "ACC-RESTRICTED-002",
            "ACC-FRAUD-001"
    );
    private static final Set<String> HIGH_RISK_ACCOUNTS = Set.of(
            "ACC-HIGH-RISK-001",
            "ACC-HIGH-RISK-002",
            "ACC-SUSPICIOUS-001"
    );

    private final Random random = new Random();
    private final Map<String, RiskLevel> riskCache = new ConcurrentHashMap<>();
    private final Map<String, Boolean> restrictionsCache = new ConcurrentHashMap<>();

    @ConfigProperty(name = "riskbureau.service.url", defaultValue = "http://localhost:8082")
    String serviceUrl;

    @ConfigProperty(name = "riskbureau.service.timeout", defaultValue = "5000")
    long timeoutMs;

    @ConfigProperty(name = "riskbureau.service.mock.enabled", defaultValue = "true")
    boolean mockEnabled;

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getAccountRiskLevelFallback")
    @Retry(name = RETRY_NAME, maxAttempts = MAX_RETRY_ATTEMPTS)
    public Uni<RiskLevel> getAccountRiskLevel(String accountNumber) {
        LOG.infof("Consultando nivel de riesgo para cuenta: %s", accountNumber);

        if (mockEnabled) {
            return mockGetAccountRiskLevel(accountNumber);
        }

        return invokeExternalRiskBureauService(accountNumber)
                .onFailure(this::isTransientError).recoverWithUni(this::handleTransientFailure)
                .onFailure().recoverWithItem(this::handlePermanentFailure);
    }

    private Uni<RiskLevel> mockGetAccountRiskLevel(String accountNumber) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            
            if (riskCache.containsKey(accountNumber)) {
                LOG.debugf("Retornando riesgo desde cache para cuenta: %s", accountNumber);
                return riskCache.get(accountNumber);
            }
            
            if (HIGH_RISK_ACCOUNTS.contains(accountNumber)) {
                LOG.warnf("Cuenta de alto riesgo detectada: %s", accountNumber);
                return RiskLevel.HIGH;
            }
            
            if (RESTRICTED_ACCOUNTS.contains(accountNumber)) {
                LOG.warnf("Cuenta con restricciones previas: %s", accountNumber);
                return RiskLevel.HIGH;
            }
            
            int riskRoll = random.nextInt(100);
            RiskLevel level;
            if (riskRoll < 10) {
                level = RiskLevel.HIGH;
            } else if (riskRoll < 30) {
                level = RiskLevel.MEDIUM;
            } else {
                level = RiskLevel.LOW;
            }
            
            riskCache.put(accountNumber, level);
            LOG.infof("Nivel de riesgo determinado para cuenta %s: %s", accountNumber, level);
            return level;
        });
    }

    private Uni<RiskLevel> invokeExternalRiskBureauService(String accountNumber) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            LOG.debugf("Invocando servicio externo de buró de riesgos: %s", serviceUrl);
            return RiskLevel.LOW;
        });
    }

    private void simulateLatency() {
        try {
            Thread.sleep(random.nextInt(150) + 50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isTransientError(Throwable t) {
        return t instanceof java.io.IOException
                || t instanceof java.util.concurrent.TimeoutException
                || (t.getCause() != null && t.getCause() instanceof java.io.IOException);
    }

    private Uni<RiskLevel> handleTransientFailure(Throwable failure) {
        LOG.warnf("Error transitorio en buró de riesgos, reintentando: %s", failure.getMessage());
        return Uni.createFrom().failure(
                new RiskBureauServiceException("Error transitorio en servicio de buró de riesgos", 
                        accountFromError(failure)));
    }

    private RiskLevel handlePermanentFailure(Throwable failure) {
        LOG.errorf("Error permanente en buró de riesgos: %s", failure.getMessage());
        return RiskLevel.MEDIUM;
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "hasAccountRestrictionsFallback")
    @Retry(name = RETRY_NAME, maxAttempts = MAX_RETRY_ATTEMPTS)
    public Uni<Boolean> hasAccountRestrictions(String accountNumber) {
        LOG.infof("Verificando restricciones para cuenta: %s", accountNumber);

        if (mockEnabled) {
            return mockHasAccountRestrictions(accountNumber);
        }

        return invokeExternalRestrictionsService(accountNumber);
    }

    private Uni<Boolean> mockHasAccountRestrictions(String accountNumber) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            
            if (restrictionsCache.containsKey(accountNumber)) {
                LOG.debugf("Retornando restricciones desde cache para cuenta: %s", accountNumber);
                return restrictionsCache.get(accountNumber);
            }
            
            boolean hasRestrictions = RESTRICTED_ACCOUNTS.contains(accountNumber);
            
            if (!hasRestrictions && random.nextInt(100) < 5) {
                hasRestrictions = true;
            }
            
            restrictionsCache.put(accountNumber, hasRestrictions);
            LOG.infof("Restricciones para cuenta %s: %s", accountNumber, hasRestrictions);
            return hasRestrictions;
        });
    }

    private Uni<Boolean> invokeExternalRestrictionsService(String accountNumber) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            LOG.debugf("Invocando servicio externo de restricciones: %s", serviceUrl);
            return Boolean.FALSE;
        });
    }

    private String accountFromError(Throwable t) {
        if (t instanceof RiskBureauServiceException) {
            return ((RiskBureauServiceException) t).getAccountNumber();
        }
        return null;
    }

    private Uni<RiskLevel> getAccountRiskLevelFallback(String accountNumber, Throwable failure) {
        LOG.errorf("Fallback de circuit breaker activado para getAccountRiskLevel: %s", failure.getMessage());
        if (failure instanceof CallNotPermittedException) {
            return Uni.createFrom().failure(
                    new RiskBureauServiceException("Buró de riesgos no disponible temporalmente", accountNumber));
        }
        return Uni.createFrom().item(RiskLevel.MEDIUM);
    }

    private Uni<Boolean> hasAccountRestrictionsFallback(String accountNumber, Throwable failure) {
        LOG.errorf("Fallback de circuit breaker activado para hasAccountRestrictions: %s", failure.getMessage());
        if (failure instanceof CallNotPermittedException) {
            return Uni.createFrom().failure(
                    new RiskBureauServiceException("Buró de riesgos no disponible temporalmente", accountNumber));
        }
        return Uni.createFrom().item(Boolean.FALSE);
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/adapters/CoreBankingAdapter.java ===
package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.ports.CoreBankingPort;
import com.financial.payments.infrastructure.exceptions.CoreBankingServiceException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class CoreBankingAdapter implements CoreBankingPort {

    private static final Logger LOGGER = Logger.getLogger(CoreBankingAdapter.class.getName());

    @ConfigProperty(name = "corebanking.api.url", defaultValue = "http://localhost:8081/api")
    String coreBankingApiUrl;

    @ConfigProperty(name = "corebanking.api.timeout", defaultValue = "5000")
    long apiTimeout;

    @ConfigProperty(name = "corebanking.api.max-connections", defaultValue = "100")
    int maxConnections;

    private final Map<String, CoreBankingAccount> accountCache = new ConcurrentHashMap<>();

    @Override
    @CircuitBreaker(name = "coreBankingCircuitBreaker", fallbackMethod = "creditAccountFallback")
    @Retry(name = "coreBankingRetry")
    public Uni<CoreBankingResult> creditAccount(Payment payment) {
        LOGGER.infof("Iniciando acreditacion para pago: %s", payment.transactionReference());

        return Uni.createFrom().item(() -> {
            String accountNumber = payment.destinationAccount();
            BigDecimal amount = payment.amount();

            CoreBankingAccount account = accountCache.get(accountNumber);
            if (account == null) {
                account = new CoreBankingAccount(accountNumber, BigDecimal.ZERO, AccountStatus.ACTIVE);
                accountCache.put(accountNumber, account);
            }

            if (account.status() != AccountStatus.ACTIVE) {
                throw new CoreBankingServiceException(
                    "Cuenta no esta activa para acreditacion: " + accountNumber,
                    "ACCOUNT_NOT_ACTIVE",
                    400
                );
            }

            BigDecimal newBalance = account.balance().add(amount);
            CoreBankingAccount updatedAccount = new CoreBankingAccount(
                accountNumber,
                newBalance,
                AccountStatus.ACTIVE
            );
            accountCache.put(accountNumber, updatedAccount);

            String coreReference = UUID.randomUUID().toString();
            Instant timestamp = Instant.now();

            LOGGER.infof("Acreditacion exitosa. Referencia core: %s, Nuevo balance: %s",
                coreReference, newBalance);

            return new CoreBankingResult(
                true,
                coreReference,
                timestamp,
                "Credito aplicado exitosamente",
                newBalance
            );
        });
    }

    @Override
    @CircuitBreaker(name = "coreBankingCircuitBreaker", fallbackMethod = "reverseCreditFallback")
    @Retry(name = "coreBankingRetry")
    public Uni<CoreBankingResult> reverseCredit(String coreReference, String reason) {
        LOGGER.infof("Iniciando reversa para referencia core: %s", coreReference);

        return Uni.createFrom().item(() -> {
            if (coreReference == null || coreReference.isBlank()) {
                throw new CoreBankingServiceException(
                    "Referencia de core invalida para reversa",
                    "INVALID_REFERENCE",
                    400
                );
            }

            Instant timestamp = Instant.now();

            LOGGER.infof("Reversa exitosa para referencia: %s", coreReference);

            return new CoreBankingResult(
                true,
                "REV-" + coreReference,
                timestamp,
                "Reversa aplicada: " + reason,
                BigDecimal.ZERO
            );
        });
    }

    @Override
    @CircuitBreaker(name = "coreBankingCircuitBreaker", fallbackMethod = "getAccountBalanceFallback")
    @Retry(name = "coreBankingRetry")
    public Uni<CoreBankingAccount> getAccountBalance(String accountNumber) {
        LOGGER.infof("Consultando balance para cuenta: %s", accountNumber);

        return Uni.createFrom().item(() -> {
            CoreBankingAccount account = accountCache.get(accountNumber);
            if (account == null) {
                account = new CoreBankingAccount(accountNumber, BigDecimal.ZERO, AccountStatus.ACTIVE);
                accountCache.put(accountNumber, account);
            }

            LOGGER.infof("Balance consultado: %s", account.balance());
            return account;
        });
    }

    private Uni<CoreBankingResult> creditAccountFallback(Payment payment, Throwable t) {
        LOGGER.warnf("Fallback activado para creditAccount. Pago: %s, Error: %s",
            payment.transactionReference(), t.getMessage());

        return Uni.createFrom().failure(new CoreBankingServiceException(
            "Core bancario no disponible despues de reintentos",
            "SERVICE_UNAVAILABLE",
            503
        ));
    }

    private Uni<CoreBankingResult> reverseCreditFallback(String coreReference, String reason, Throwable t) {
        LOGGER.warnf("Fallback activado para reverseCredit. Referencia: %s, Error: %s",
            coreReference, t.getMessage());

        return Uni.createFrom().failure(new CoreBankingServiceException(
            "No se pudo reversar el credito en el core bancario",
            "REVERSAL_FAILED",
            500
        ));
    }

    private Uni<CoreBankingAccount> getAccountBalanceFallback(String accountNumber, Throwable t) {
        LOGGER.warnf("Fallback activado para getAccountBalance. Cuenta: %s, Error: %s",
            accountNumber, t.getMessage());

        return Uni.createFrom().failure(new CoreBankingServiceException(
            "No se pudo consultar el balance de la cuenta",
            "BALANCE_QUERY_FAILED",
            503
        ));
    }

    public record CoreBankingResult(
        boolean success,
        String coreReference,
        Instant timestamp,
        String message,
        BigDecimal newBalance
    ) {}

    public record CoreBankingAccount(
        String accountNumber,
        BigDecimal balance,
        AccountStatus status
    ) {
        public CoreBankingAccount {
            if (accountNumber == null || accountNumber.isBlank()) {
                throw new IllegalArgumentException("Numero de cuenta no puede ser nulo o vacio");
            }
            if (balance == null) {
                throw new IllegalArgumentException("Balance no puede ser nulo");
            }
            if (status == null) {
                throw new IllegalArgumentException("Estado de cuenta no puede ser nulo");
            }
        }
    }

    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        BLOCKED,
        CLOSED
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/adapters/PaymentGatewayAdapter.java ===
package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.ports.PaymentGatewayPort;
import com.financial.payments.infrastructure.exceptions.PaymentGatewayServiceException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class PaymentGatewayAdapter implements PaymentGatewayPort {

    private static final Logger LOGGER = Logger.getLogger(PaymentGatewayAdapter.class.getName());

    @ConfigProperty(name = "paymentgateway.api.url", defaultValue = "http://localhost:8082/api")
    String gatewayApiUrl;

    @ConfigProperty(name = "paymentgateway.api.timeout", defaultValue = "10000")
    long apiTimeout;

    @ConfigProperty(name = "paymentgateway.api.key", defaultValue = "default-api-key")
    String apiKey;

    @ConfigProperty(name = "paymentgateway.max-amount", defaultValue = "100000")
    BigDecimal maxAmount;

    private final Map<String, GatewayTransaction> transactionCache = new ConcurrentHashMap<>();

    @Override
    @CircuitBreaker(name = "paymentGatewayCircuitBreaker", fallbackMethod = "processPaymentFallback")
    @Retry(name = "paymentGatewayRetry")
    public Uni<GatewayResponse> processPayment(Payment payment) {
        LOGGER.infof("Procesando pago en gateway: %s", payment.transactionReference());

        return Uni.createFrom().item(() -> {
            if (payment.amount().compareTo(maxAmount) > 0) {
                throw new PaymentGatewayServiceException(
                    "Monto excede el limite maximo del gateway: " + maxAmount,
                    "AMOUNT_EXCEEDS_LIMIT",
                    400
                );
            }

            String gatewayTransactionId = UUID.randomUUID().toString();
            Instant timestamp = Instant.now();

            GatewayTransaction transaction = new GatewayTransaction(
                gatewayTransactionId,
                payment.transactionReference(),
                payment.amount(),
                payment.currency(),
                TransactionStatus.PROCESSED,
                timestamp
            );

            transactionCache.put(gatewayTransactionId, transaction);

            LOGGER.infof("Pago procesado exitosamente en gateway. ID: %s", gatewayTransactionId);

            return new GatewayResponse(
                true,
                gatewayTransactionId,
                timestamp,
                "Pago procesado exitosamente",
                "APPROVED",
                "00"
            );
        });
    }

    @Override
    @CircuitBreaker(name = "paymentGatewayCircuitBreaker", fallbackMethod = "refundPaymentFallback")
    @Retry(name = "paymentGatewayRetry")
    public Uni<GatewayResponse> refundPayment(String gatewayTransactionId, BigDecimal amount, String reason) {
        LOGGER.infof("Iniciando reembolso en gateway. ID: %s, Monto: %s",
            gatewayTransactionId, amount);

        return Uni.createFrom().item(() -> {
            GatewayTransaction original = transactionCache.get(gatewayTransactionId);

            if (original == null) {
                throw new PaymentGatewayServiceException(
                    "Transaccion no encontrada en gateway: " + gatewayTransactionId,
                    "TRANSACTION_NOT_FOUND",
                    404
                );
            }

            if (amount.compareTo(original.amount()) > 0) {
                throw new PaymentGatewayServiceException(
                    "Monto de reembolso excede el monto original",
                    "REFUND_AMOUNT_EXCEEDS",
                    400
                );
            }

            String refundId = UUID.randomUUID().toString();
            Instant timestamp = Instant.now();

            LOGGER.infof("Reembolso procesado. ID: %s", refundId);

            return new GatewayResponse(
                true,
                refundId,
                timestamp,
                "Reembolso aplicado: " + reason,
                "REFUNDED",
                "00"
            );
        });
    }

    @Override
    @CircuitBreaker(name = "paymentGatewayCircuitBreaker", fallbackMethod = "getTransactionStatusFallback")
    @Retry(name = "paymentGatewayRetry")
    public Uni<GatewayResponse> getTransactionStatus(String gatewayTransactionId) {
        LOGGER.infof("Consultando estado de transaccion en gateway: %s", gatewayTransactionId);

        return Uni.createFrom().item(() -> {
            GatewayTransaction transaction = transactionCache.get(gatewayTransactionId);

            if (transaction == null) {
                throw new PaymentGatewayServiceException(
                    "Transaccion no encontrada: " + gatewayTransactionId,
                    "TRANSACTION_NOT_FOUND",
                    404
                );
            }

            String statusCode = mapStatusToCode(transaction.status());

            return new GatewayResponse(
                true,
                gatewayTransactionId,
                transaction.timestamp(),
                "Estado: " + transaction.status(),
                transaction.status().name(),
                statusCode
            );
        });
    }

    @Override
    @CircuitBreaker(name = "paymentGatewayCircuitBreaker", fallbackMethod = "validateCardFallback")
    @Retry(name = "paymentGatewayRetry")
    public Uni<CardValidationResult> validateCard(String cardNumber, String cvv, String expiryDate) {
        LOGGER.infof("Validando tarjeta para autenticacion");

        return Uni.createFrom().item(() -> {
            if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
                throw new PaymentGatewayServiceException(
                    "Numero de tarjeta invalido",
                    "INVALID_CARD",
                    400
                );
            }

            if (cvv == null || cvv.length() < 3 || cvv.length() > 4) {
                throw new PaymentGatewayServiceException(
                    "CVV invalido",
                    "INVALID_CVV",
                    400
                );
            }

            boolean isValid = luhnCheck(cardNumber);

            return new CardValidationResult(
                isValid,
                isValid ? "Tarjeta valida" : "Tarjeta invalida segun algoritmo de Luhn",
                detectCardType(cardNumber)
            );
        });
    }

    private Uni<GatewayResponse> processPaymentFallback(Payment payment, Throwable t) {
        LOGGER.warnf("Fallback activado para processPayment. Pago: %s, Error: %s",
            payment.transactionReference(), t.getMessage());

        return Uni.createFrom().failure(new PaymentGatewayServiceException(
            "Gateway de pagos no disponible despues de reintentos",
            "SERVICE_UNAVAILABLE",
            503
        ));
    }

    private Uni<GatewayResponse> refundPaymentFallback(String gatewayTransactionId,
                                                        BigDecimal amount, String reason, Throwable t) {
        LOGGER.warnf("Fallback activado para refundPayment. ID: %s, Error: %s",
            gatewayTransactionId, t.getMessage());

        return Uni.createFrom().failure(new PaymentGatewayServiceException(
            "No se pudo procesar el reembolso en el gateway",
            "REFUND_FAILED",
            500
        ));
    }

    private Uni<GatewayResponse> getTransactionStatusFallback(String gatewayTransactionId, Throwable t) {
        LOGGER.warnf("Fallback activado para getTransactionStatus. ID: %s, Error: %s",
            gatewayTransactionId, t.getMessage());

        return Uni.createFrom().failure(new PaymentGatewayServiceException(
            "No se pudo consultar el estado de la transaccion",
            "STATUS_QUERY_FAILED",
            503
        ));
    }

    private Uni<CardValidationResult> validateCardFallback(String cardNumber, String cvv,
                                                            String expiryDate, Throwable t) {
        LOGGER.warnf("Fallback activado para validateCard. Error: %s", t.getMessage());

        return Uni.createFrom().failure(new PaymentGatewayServiceException(
            "No se pudo validar la tarjeta",
            "VALIDATION_FAILED",
            503
        ));
    }

    private boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10) == 0;
    }

    private String detectCardType(String cardNumber) {
        String prefix = cardNumber.substring(0, 1);

        return switch (prefix) {
            case "4" -> "VISA";
            case "5" -> "MASTERCARD";
            case "3" -> "AMEX";
            case "6" -> "DISCOVER";
            default -> "UNKNOWN";
        };
    }

    private String mapStatusToCode(TransactionStatus status) {
        return switch (status) {
            case PROCESSED -> "00";
            case PENDING -> "01";
            case FAILED -> "02";
            case REFUNDED -> "03";
            case CANCELLED -> "04";
        };
    }

    public record GatewayResponse(
        boolean success,
        String transactionId,
        Instant timestamp,
        String message,
        String status,
        String code
    ) {}

    public record CardValidationResult(
        boolean isValid,
        String message,
        String cardType
    ) {}

    public record GatewayTransaction(
        String gatewayTransactionId,
        String paymentReference,
        BigDecimal amount,
        String currency,
        TransactionStatus status,
        Instant timestamp
    ) {}

    public enum TransactionStatus {
        PENDING,
        PROCESSED,
        FAILED,
        REFUNDED,
        CANCELLED
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/config/ResilienceConfig.java ===
package com.financial.payments.infrastructure.config;

import io.quarkus.arc.profile.IfBuildProfile;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigMapping(prefix = "resilience4j")
@IfBuildProfile(not = "test")
public interface ResilienceConfig {

    CircuitBreakerConfig circuitBreaker();

    RetryConfig retry();

    RateLimiterConfig rateLimiter();

    interface CircuitBreakerConfig {
        @WithDefault("5")
        int failureRateThreshold();

        @WithDefault("60")
        int waitDurationInOpenState();

        @WithDefault("10")
        int slidingWindowSize();

        @WithDefault("50")
        int minimumNumberOfCalls();
    }

    interface RetryConfig {
        @WithDefault("3")
        int maxAttempts();

        @WithDefault("1000")
        long waitDuration();

        @WithDefault("1.5")
        double multiplier();
    }

    interface RateLimiterConfig {
        @WithDefault("100")
        int limitForPeriod();

        @WithDefault("1000")
        int limitRefreshPeriod();
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/exceptions/AntiFraudServiceException.java ===
package com.financial.payments.infrastructure.exceptions;

import java.util.Optional;

public class AntiFraudServiceException extends RuntimeException {

    private final String serviceName;
    private final ErrorCode errorCode;
    private final String transactionReference;
    private final boolean retryable;

    public AntiFraudServiceException(String message) {
        super(message);
        this.serviceName = "AntiFraudService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.transactionReference = null;
        this.retryable = false;
    }

    public AntiFraudServiceException(String message, Throwable cause) {
        super(message, cause);
        this.serviceName = "AntiFraudService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.transactionReference = null;
        this.retryable = determineRetryability(cause);
    }

    public AntiFraudServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.serviceName = "AntiFraudService";
        this.errorCode = errorCode;
        this.transactionReference = null;
        this.retryable = errorCode.isRetryable();
    }

    public AntiFraudServiceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.serviceName = "AntiFraudService";
        this.errorCode = errorCode;
        this.transactionReference = null;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
    }

    public AntiFraudServiceException(String message, Throwable cause, ErrorCode errorCode, String transactionReference) {
        super(message, cause);
        this.serviceName = "AntiFraudService";
        this.errorCode = errorCode;
        this.transactionReference = transactionReference;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
    }

    private static boolean determineRetryability(Throwable cause) {
        if (cause == null) {
            return false;
        }
        String exceptionClassName = cause.getClass().getSimpleName().toLowerCase();
        return exceptionClassName.contains("timeout") ||
               exceptionClassName.contains("connection") ||
               exceptionClassName.contains("unavailable");
    }

    public String getServiceName() {
        return serviceName;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Optional<String> getTransactionReference() {
        return Optional.ofNullable(transactionReference);
    }

    public boolean isRetryable() {
        return retryable;
    }

    public int getHttpStatusCode() {
        return errorCode.getHttpStatus();
    }

    public static AntiFraudServiceException timeout(String transactionReference, long timeoutMs) {
        return new AntiFraudServiceException(
            "Timeout al verificar riesgo de fraude. Transacción: " + transactionReference + ". Timeout: " + timeoutMs + "ms",
            ErrorCode.SERVICE_TIMEOUT,
            transactionReference
        );
    }

    public static AntiFraudServiceException connectionFailed(String transactionReference) {
        return new AntiFraudServiceException(
            "Conexión fallida con el servicio de antifraude. Transacción: " + transactionReference,
            ErrorCode.SERVICE_UNAVAILABLE,
            transactionReference
        );
    }

    public static AntiFraudServiceException invalidResponse(String transactionReference, String details) {
        return new AntiFraudServiceException(
            "Respuesta inválida del servicio de antifraude. Transacción: " + transactionReference + ". Detalles: " + details,
            ErrorCode.INVALID_RESPONSE,
            transactionReference
        );
    }

    public static AntiFraudServiceException fraudDetectionFailed(String transactionReference) {
        return new AntiFraudServiceException(
            "Error en la detección de fraude para la transacción: " + transactionReference,
            ErrorCode.FRAUD_CHECK_FAILED,
            transactionReference
        );
    }

    public enum ErrorCode {
        UNKNOWN_ERROR(500, false),
        SERVICE_UNAVAILABLE(503, true),
        SERVICE_TIMEOUT(504, true),
        INVALID_RESPONSE(502, false),
        FRAUD_CHECK_FAILED(500, false),
        RISK_EVALUATION_ERROR(500, false),
        HIGH_RISK_DETECTED(500, false),
        INTERNAL_ERROR(500, false);

        private final int httpStatus;
        private final boolean retryable;

        ErrorCode(int httpStatus, boolean retryable) {
            this.httpStatus = httpStatus;
            this.retryable = retryable;
        }

        public int getHttpStatus() {
            return httpStatus;
        }

        public boolean isRetryable() {
            return retryable;
        }
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/exceptions/RiskBureauServiceException.java ===
package com.financial.payments.infrastructure.exceptions;

import java.util.Optional;

public class RiskBureauServiceException extends RuntimeException {

    private final String serviceName;
    private final ErrorCode errorCode;
    private final String accountNumber;
    private final boolean retryable;
    private final long retryAfterSeconds;

    public RiskBureauServiceException(String message) {
        super(message);
        this.serviceName = "RiskBureauService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.accountNumber = null;
        this.retryable = false;
        this.retryAfterSeconds = 0;
    }

    public RiskBureauServiceException(String message, Throwable cause) {
        super(message, cause);
        this.serviceName = "RiskBureauService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.accountNumber = null;
        this.retryable = determineRetryability(cause);
        this.retryAfterSeconds = 0;
    }

    public RiskBureauServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.serviceName = "RiskBureauService";
        this.errorCode = errorCode;
        this.accountNumber = null;
        this.retryable = errorCode.isRetryable();
        this.retryAfterSeconds = errorCode.getDefaultRetrySeconds();
    }

    public RiskBureauServiceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.serviceName = "RiskBureauService";
        this.errorCode = errorCode;
        this.accountNumber = null;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterSeconds = errorCode.getDefaultRetrySeconds();
    }

    public RiskBureauServiceException(String message, Throwable cause, ErrorCode errorCode, String accountNumber) {
        super(message, cause);
        this.serviceName = "RiskBureauService";
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterSeconds = errorCode.getDefaultRetrySeconds();
    }

    public RiskBureauServiceException(String message, Throwable cause, ErrorCode errorCode, String accountNumber, long retryAfterSeconds) {
        super(message, cause);
        this.serviceName = "RiskBureauService";
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterSeconds = retryAfterSeconds;
    }

    private static boolean determineRetryability(Throwable cause) {
        if (cause == null) {
            return false;
        }
        String exceptionClassName = cause.getClass().getSimpleName().toLowerCase();
        return exceptionClassName.contains("timeout") ||
               exceptionClassName.contains("connection") ||
               exceptionClassName.contains("unavailable");
    }

    public String getServiceName() {
        return serviceName;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Optional<String> getAccountNumber() {
        return Optional.ofNullable(accountNumber);
    }

    public boolean isRetryable() {
        return retryable;
    }

    public long getRetryAfterSeconds() {
        return retryAfterSeconds;
    }

    public int getHttpStatusCode() {
        return errorCode.getHttpStatus();
    }

    public static RiskBureauServiceException timeout(String accountNumber, long timeoutMs) {
        return new RiskBureauServiceException(
            "Timeout al consultar buró de riesgos para cuenta: " + accountNumber + ". Timeout: " + timeoutMs + "ms",
            ErrorCode.SERVICE_TIMEOUT,
            accountNumber
        );
    }

    public static RiskBureauServiceException connectionFailed(String accountNumber) {
        return new RiskBureauServiceException(
            "Conexión fallida con el buró de riesgos para cuenta: " + accountNumber,
            ErrorCode.SERVICE_UNAVAILABLE,
            accountNumber
        );
    }

    public static RiskBureauServiceException accountNotFound(String accountNumber) {
        return new RiskBureauServiceException(
            "Cuenta no encontrada en el buró de riesgos: " + accountNumber,
            ErrorCode.ACCOUNT_NOT_FOUND,
            accountNumber
        );
    }

    public static RiskBureauServiceException riskEvaluationFailed(String accountNumber, String details) {
        return new RiskBureauServiceException(
            "Error al evaluar riesgo de la cuenta: " + accountNumber + ". Detalles: " + details,
            ErrorCode.RISK_EVALUATION_FAILED,
            accountNumber
        );
    }

    public static RiskBureauServiceException restrictionsFound(String accountNumber, String restrictionDetails) {
        return new RiskBureauServiceException(
            "Restricciones encontradas para la cuenta: " + accountNumber + ". Detalles: " + restrictionDetails,
            ErrorCode.ACCOUNT_RESTRICTIONS,
            accountNumber
        );
    }

    public enum ErrorCode {
        UNKNOWN_ERROR(500, false, 0),
        SERVICE_UNAVAILABLE(503, true, 30),
        SERVICE_TIMEOUT(504, true, 15),
        ACCOUNT_NOT_FOUND(404, false, 0),
        RISK_EVALUATION_FAILED(500, false, 0),
        ACCOUNT_RESTRICTIONS(403, false, 0),
        HIGH_RISK_ACCOUNT(500, false, 0),
        INTERNAL_ERROR(500, false, 0);

        private final int httpStatus;
        private final boolean retryable;
        private final long defaultRetrySeconds;

        ErrorCode(int httpStatus, boolean retryable, long defaultRetrySeconds) {
            this.httpStatus = httpStatus;
            this.retryable = retryable;
            this.defaultRetrySeconds = defaultRetrySeconds;
        }

        public int getHttpStatus() {
            return httpStatus;
        }

        public boolean isRetryable() {
            return retryable;
        }

        public long getDefaultRetrySeconds() {
            return defaultRetrySeconds;
        }
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/exceptions/CoreBankingServiceException.java ===
package com.financial.payments.infrastructure.exceptions;

import java.util.Optional;

public class CoreBankingServiceException extends RuntimeException {

    private final String serviceName;
    private final ErrorCode errorCode;
    private final String accountNumber;
    private final String transactionReference;
    private final boolean retryable;
    private final long retryAfterMillis;

    public CoreBankingServiceException(String message) {
        super(message);
        this.serviceName = "CoreBankingService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.accountNumber = null;
        this.transactionReference = null;
        this.retryable = false;
        this.retryAfterMillis = 0;
    }

    public CoreBankingServiceException(String message, Throwable cause) {
        super(message, cause);
        this.serviceName = "CoreBankingService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.accountNumber = null;
        this.transactionReference = null;
        this.retryable = determineRetryability(cause);
        this.retryAfterMillis = 0;
    }

    public CoreBankingServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.serviceName = "CoreBankingService";
        this.errorCode = errorCode;
        this.accountNumber = null;
        this.transactionReference = null;
        this.retryable = errorCode.isRetryable();
        this.retryAfterMillis = errorCode.getDefaultRetryMillis();
    }

    public CoreBankingServiceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.serviceName = "CoreBankingService";
        this.errorCode = errorCode;
        this.accountNumber = null;
        this.transactionReference = null;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterMillis = errorCode.getDefaultRetryMillis();
    }

    public CoreBankingServiceException(String message, Throwable cause, ErrorCode errorCode, String accountNumber) {
        super(message, cause);
        this.serviceName = "CoreBankingService";
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
        this.transactionReference = null;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterMillis = errorCode.getDefaultRetryMillis();
    }

    public CoreBankingServiceException(String message, Throwable cause, ErrorCode errorCode, String accountNumber, String transactionReference) {
        super(message, cause);
        this.serviceName = "CoreBankingService";
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
        this.transactionReference = transactionReference;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterMillis = errorCode.getDefaultRetryMillis();
    }

    public CoreBankingServiceException(String message, Throwable cause, ErrorCode errorCode, String accountNumber, String transactionReference, long retryAfterMillis) {
        super(message, cause);
        this.serviceName = "CoreBankingService";
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
        this.transactionReference = transactionReference;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterMillis = retryAfterMillis;
    }

    private static boolean determineRetryability(Throwable cause) {
        if (cause == null) {
            return false;
        }
        String exceptionClassName = cause.getClass().getSimpleName().toLowerCase();
        return exceptionClassName.contains("timeout") ||
               exceptionClassName.contains("connection") ||
               exceptionClassName.contains("unavailable");
    }

    public String getServiceName() {
        return serviceName;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Optional<String> getAccountNumber() {
        return Optional.ofNullable(accountNumber);
    }

    public Optional<String> getTransactionReference() {
        return Optional.ofNullable(transactionReference);
    }

    public boolean isRetryable() {
        return retryable;
    }

    public long getRetryAfterMillis() {
        return retryAfterMillis;
    }

    public int getHttpStatusCode() {
        return errorCode.getHttpStatus();
    }

    public static CoreBankingServiceException timeout(String accountNumber, String transactionReference, long timeoutMs) {
        return new CoreBankingServiceException(
            "Timeout al procesar en core bancario. Cuenta: " + accountNumber + ", Transacción: " + transactionReference + ", Timeout: " + timeoutMs + "ms",
            ErrorCode.SERVICE_TIMEOUT,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException connectionFailed(String accountNumber, String transactionReference) {
        return new CoreBankingServiceException(
            "Conexión fallida con el core bancario. Cuenta: " + accountNumber + ", Transacción: " + transactionReference,
            ErrorCode.SERVICE_UNAVAILABLE,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException insufficientFunds(String accountNumber, String transactionReference, java.math.BigDecimal available, java.math.BigDecimal required) {
        return new CoreBankingServiceException(
            "Fondos insuficientes en cuenta: " + accountNumber + ", Transacción: " + transactionReference + ". Disponible: " + available + ", Requerido: " + required,
            ErrorCode.INSUFFICIENT_FUNDS,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException accountNotFound(String accountNumber, String transactionReference) {
        return new CoreBankingServiceException(
            "Cuenta no encontrada en el core bancario: " + accountNumber + ", Transacción: " + transactionReference,
            ErrorCode.ACCOUNT_NOT_FOUND,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException accountClosed(String accountNumber, String transactionReference) {
        return new CoreBankingServiceException(
            "Cuenta cerrada en el core bancario: " + accountNumber + ", Transacción: " + transactionReference,
            ErrorCode.ACCOUNT_CLOSED,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException transactionFailed(String accountNumber, String transactionReference, String reason) {
        return new CoreBankingServiceException(
            "Transacción fallida en el core bancario. Cuenta: " + accountNumber + ", Transacción: " + transactionReference + ", Razón: " + reason,
            ErrorCode.TRANSACTION_FAILED,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException duplicateTransaction(String accountNumber, String transactionReference) {
        return new CoreBankingServiceException(
            "Transacción duplicada detectada en el core bancario. Cuenta: " + accountNumber + ", Transacción: " + transactionReference,
            ErrorCode.DUPLICATE_TRANSACTION,
            accountNumber,
            transactionReference
        );
    }

    public enum ErrorCode {
        UNKNOWN_ERROR(500, false, 0),
        SERVICE_UNAVAILABLE(503, true, 30000),
        SERVICE_TIMEOUT(504, true, 15000),
        ACCOUNT_NOT_FOUND(404, false, 0),
        ACCOUNT_CLOSED(403, false, 0),
        INSUFFICIENT_FUNDS(402, false, 0),
        TRANSACTION_FAILED(500, false, 0),
        DUPLICATE_TRANSACTION(409, false, 0),
        INVALID_AMOUNT(400, false, 0),
        INTERNAL_ERROR(500, false, 0);

        private final int httpStatus;
        private final boolean retryable;
        private final long defaultRetryMillis;

        ErrorCode(int httpStatus, boolean retryable, long defaultRetryMillis) {
            this.httpStatus = httpStatus;
            this.retryable = retryable;
            this.defaultRetryMillis = defaultRetryMillis;
        }

        public int getHttpStatus() {
            return httpStatus;
        }

        public boolean isRetryable() {
            return retryable;
        }

        public long getDefaultRetryMillis() {
            return defaultRetryMillis;
        }
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/exceptions/GlobalExceptionHandler.java ===
package com.financial.payments.infrastructure.exceptions;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.ports.AntiFraudPort;
import com.financial.payments.domain.ports.RiskBureauPort;

import io.smallrye.mutiny.Uni;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Inject
    AntiFraudPort antiFraudPort;

    @Inject
    RiskBureauPort riskBureauPort;

    @Override
    public Response toResponse(Exception exception) {
        LOG.error("Excepcion capturada en el manejador global", exception);

        if (exception instanceof AntiFraudServiceException afEx) {
            return handleAntiFraudException(afEx);
        } else if (exception instanceof RiskBureauServiceException rbEx) {
            return handleRiskBureauException(rbEx);
        } else if (exception instanceof CoreBankingServiceException cbEx) {
            return handleCoreBankingException(cbEx);
        } else if (exception instanceof IllegalArgumentException iaEx) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, "BAD_REQUEST", iaEx.getMessage());
        } else if (exception instanceof IllegalStateException isEx) {
            return buildErrorResponse(Response.Status.CONFLICT, "CONFLICT", isEx.getMessage());
        }

        return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                "Ocurrio un error inesperado al procesar el pago");
    }

    private Response handleAntiFraudException(AntiFraudServiceException ex) {
        String errorCode = "ANTIFRAUD_FAILURE";
        String message = "El sistema de prevencion de fraude no esta disponible temporalmente";

        LOG.warnf("AntiFraudServiceException: %s - %s", ex.getMessage(), message);

        return buildErrorResponse(Response.Status.SERVICE_UNAVAILABLE, errorCode, message);
    }

    private Response handleRiskBureauException(RiskBureauServiceException ex) {
        String errorCode = "RISK_BUREAU_FAILURE";
        String message = "El buró de riesgos no respondio a tiempo. Intente mas tarde";

        LOG.warnf("RiskBureauServiceException: %s - %s", ex.getMessage(), message);

        return buildErrorResponse(Response.Status.GATEWAY_TIMEOUT, errorCode, message);
    }

    private Response handleCoreBankingException(CoreBankingServiceException ex) {
        String errorCode = "CORE_BANKING_FAILURE";
        String message = "El core bancario rechazo la transaccion";

        LOG.warnf("CoreBankingServiceException: %s - %s", ex.getMessage(), message);

        if (ex.getMessage() != null && ex.getMessage().contains("INSUFFICIENT_FUNDS")) {
            errorCode = "INSUFFICIENT_FUNDS";
            message = "Fondos insuficientes en la cuenta origen";
            return buildErrorResponse(Response.Status.PAYMENT_REQUIRED, errorCode, message);
        }

        return buildErrorResponse(Response.Status.BAD_GATEWAY, errorCode, message);
    }

    private Response buildErrorResponse(Response.Status status, String errorCode, String message) {
        ErrorResponse error = new ErrorResponse(errorCode, message, System.currentTimeMillis());
        return Response.status(status)
                .entity(error)
                .build();
    }

    public record ErrorResponse(
        String errorCode,
        String message,
        long timestamp
    ) {}

    public Uni<Response> handleAntiFraudReactive(AntiFraudServiceException ex, Payment payment) {
        LOG.warnf("Excepcion reactiva de AntiFraud para pago %s: %s",
                payment.transactionReference(), ex.getMessage());

        String errorCode = "ANTIFRAUD_RETRY_EXCEEDED";
        String message = "Se excedio el numero de reintentos con el servicio antifraude";

        ErrorResponse error = new ErrorResponse(errorCode, message, System.currentTimeMillis());

        return Uni.createFrom().item(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(error)
                .build());
    }

    public Uni<Response> handleRiskBureauReactive(RiskBureauServiceException ex, String accountNumber) {
        LOG.warnf("Excepcion reactiva de RiskBureau para cuenta %s: %s",
                accountNumber, ex.getMessage());

        String errorCode = "RISK_CHECK_TIMEOUT";
        String message = "Timeout al consultar el buró de riesgos";

        ErrorResponse error = new ErrorResponse(errorCode, message, System.currentTimeMillis());

        return Uni.createFrom().item(Response.status(Response.Status.GATEWAY_TIMEOUT)
                .entity(error)
                .build());
    }

    public Uni<Response> handleValidationError(String field, String reason) {
        String errorCode = "VALIDATION_ERROR";
        String message = String.format("Validacion fallida para el campo '%s': %s", field, reason);

        LOG.warnf("Error de validacion: %s", message);

        ErrorResponse error = new ErrorResponse(errorCode, message, System.currentTimeMillis());

        return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build());
    }
}

// === ARCHIVO: src/main/java/com/financial/payments/infrastructure/rest/PaymentResource.java ===
package com.financial.payments.infrastructure.rest;




import com.financial.payments.infrastructure.exceptions.ErrorResponse;
import com.financial.payments.infrastructure.exceptions.RiskBureauServiceException;
import com.financial.payments.infrastructure.exceptions.AntiFraudServiceException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import com.financial.payments.application.usecases.ProcessPaymentUseCase;
import com.financial.payments.domain.model.Payment;
import com.financial.payments.infrastructure.exceptions.GlobalExceptionHandler;

import io.smallrye.mutiny.Uni;

@Path("/api/v1/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    private static final Logger LOG = Logger.getLogger(PaymentResource.class);

    @Inject
    ProcessPaymentUseCase processPaymentUseCase;

    @Inject
    GlobalExceptionHandler exceptionHandler;

    @Inject
    @Channel("payment-requests")
    Emitter<Payment> paymentEmitter;

    @POST
    @Path("/submit")
    public Uni<Response> submitPayment(PaymentRequest request) {
        LOG.infof("Recibida solicitud de pago: %s", request);

        if (request == null) {
            return exceptionHandler.handleValidationError("request", "El cuerpo de la solicitud no puede ser nulo");
        }

        if (request.accountNumber() == null || request.accountNumber().isBlank()) {
            return exceptionHandler.handleValidationError("accountNumber", "El numero de cuenta es obligatorio");
        }

        if (request.amount() == null || request.amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return exceptionHandler.handleValidationError("amount", "El monto debe ser mayor que cero");
        }

        if (request.currency() == null || request.currency().isBlank()) {
            return exceptionHandler.handleValidationError("currency", "La moneda es obligatoria");
        }

        if (request.description() == null || request.description().isBlank()) {
            return exceptionHandler.handleValidationError("description", "La descripcion es obligatoria");
        }

        Payment payment = new Payment(
                request.accountNumber(),
                request.amount(),
                request.currency(),
                request.description(),
                request.recipientAccount(),
                request.recipientName()
        );

        LOG.infof("Payment creado con referencia: %s", payment.transactionReference());

        return processPaymentUseCase.execute(payment)
                .onItem().transform(p -> {
                    PaymentResponse response = new PaymentResponse(
                            p.transactionReference(),
                            p.status().name(),
                            "Pago procesado exitosamente",
                            p.createdAt()
                    );
                    LOG.infof("Pago procesado exitosamente: %s", p.transactionReference());
                    return Response.status(Response.Status.ACCEPTED)
                            .entity(response)
                            .build();
                })
                .onFailure().recoverWithUni(throwable -> {
                    LOG.errorf("Error al procesar pago: %s", throwable.getMessage());

                    if (throwable instanceof com.financial.payments.infrastructure.exceptions.AntiFraudServiceException afEx) {
                        return exceptionHandler.handleAntiFraudReactive(afEx, payment);
                    } else if (throwable instanceof com.financial.payments.infrastructure.exceptions.RiskBureauServiceException rbEx) {
                        return exceptionHandler.handleRiskBureauReactive(rbEx, request.accountNumber());
                    } else if (throwable instanceof IllegalArgumentException iaEx) {
                        return exceptionHandler.handleValidationError("payment", iaEx.getMessage());
                    }

                    return Uni.createFrom().item(
                            Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                    .entity(new GlobalExceptionHandler.ErrorResponse(
                                            "PROCESSING_ERROR",
                                            "Error al procesar el pago: " + throwable.getMessage(),
                                            System.currentTimeMillis()
                                    ))
                                    .build()
                    );
                });
    }

    @POST
    @Path("/async-submit")
    public Uni<Response> submitPaymentAsync(PaymentRequest request) {
        LOG.infof("Recibida solicitud de pago asincrona: %s", request);

        if (request == null || request.accountNumber() == null || request.amount() == null) {
            return exceptionHandler.handleValidationError("request", "Solicitud invalida");
        }

        Payment payment = new Payment(
                request.accountNumber(),
                request.amount(),
                request.currency(),
                request.description(),
                request.recipientAccount(),
                request.recipientName()
        );

        paymentEmitter.send(payment);

        LOG.infof("Pago encolado para procesamiento asincrono: %s", payment.transactionReference());

        AsyncPaymentResponse response = new AsyncPaymentResponse(
                payment.transactionReference(),
                "Pago recibido y en cola para procesamiento",
                System.currentTimeMillis()
        );

        return Uni.createFrom().item(Response.status(Response.Status.ACCEPTED)
                .entity(response)
                .build());
    }

    public record PaymentRequest(
        String accountNumber,
        java.math.BigDecimal amount,
        String currency,
        String description,
        String recipientAccount,
        String recipientName
    ) {}

    public record PaymentResponse(
        String transactionReference,
        String status,
        String message,
        java.time.Instant timestamp
    ) {}

    public record AsyncPaymentResponse(
        String transactionReference,
        String message,
        long timestamp
    ) {}
}

// === ARCHIVO: src/test/java/com/financial/payments/application/usecases/ProcessPaymentUseCaseTest.java ===
package com.financial.payments.application.usecases;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.model.Payment.PaymentStatus;
import com.financial.payments.domain.ports.AntiFraudPort;
import com.financial.payments.domain.ports.CoreBankingPort;
import com.financial.payments.domain.ports.PaymentGatewayPort;
import com.financial.payments.domain.ports.RiskBureauPort;
import com.financial.payments.domain.ports.RiskBureauPort.RiskLevel;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class ProcessPaymentUseCaseTest {

    @Inject
    ProcessPaymentUseCase processPaymentUseCase;

    @InjectMock
    AntiFraudPort antiFraudPort;

    @InjectMock
    RiskBureauPort riskBureauPort;

    @InjectMock
    CoreBankingPort coreBankingPort;

    @InjectMock
    PaymentGatewayPort paymentGatewayPort;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        testPayment = new Payment(
            "TXN-" + System.currentTimeMillis(),
            "ACC-12345",
            "ACC-67890",
            new BigDecimal("1000.00"),
            "USD",
            PaymentStatus.PENDING,
            LocalDateTime.now(),
            0,
            null
        );
    }

    @Test
    @DisplayName("Debe procesar pago exitosamente cuando todos los servicios responden correctamente")
    void testProcessPayment_Success() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.LOW));
        when(riskBureauPort.hasAccountRestrictions(anyString())).thenReturn(Uni.createFrom().item(false));
        when(coreBankingPort.reserveFunds(anyString(), any(BigDecimal.class))).thenReturn(Uni.createFrom().item(true));
        when(paymentGatewayPort.executePayment(any(Payment.class))).thenReturn(Uni.createFrom().item("PAY-OK"));

        Payment result = processPaymentUseCase.execute(testPayment).await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.COMPLETED, result.status());
        verify(antiFraudPort, times(1)).verifyFraudRisk(any(Payment.class));
        verify(paymentGatewayPort, times(1)).executePayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe rechazar pago cuando el motor antifraude detecta riesgo")
    void testProcessPayment_RejectedByAntiFraud() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(false));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(85));

        Payment result = processPaymentUseCase.execute(testPayment).await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.status());
        verify(coreBankingPort, never()).reserveFunds(anyString(), any(BigDecimal.class));
        verify(paymentGatewayPort, never()).executePayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe rechazar pago cuando el buró de riesgos indica nivel alto")
    void testProcessPayment_RejectedByRiskBureau_HighRisk() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.HIGH));

        Payment result = processPaymentUseCase.execute(testPayment).await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.status());
    }

    @Test
    @DisplayName("Debe rechazar pago cuando el buró de riesgos detecta restricciones")
    void testProcessPayment_RejectedByRiskBureau_Restrictions() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.LOW));
        when(riskBureauPort.hasAccountRestrictions(anyString())).thenReturn(Uni.createFrom().item(true));

        Payment result = processPaymentUseCase.execute(testPayment).await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.status());
    }

    @Test
    @DisplayName("Debe fallar cuando la reserva de fondos en core bancario falla")
    void testProcessPayment_FundsReservationFailure() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.LOW));
        when(riskBureauPort.hasAccountRestrictions(anyString())).thenReturn(Uni.createFrom().item(false));
        when(coreBankingPort.reserveFunds(anyString(), any(BigDecimal.class))).thenReturn(Uni.createFrom().item(false));

        Payment result = processPaymentUseCase.execute(testPayment).await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.FAILED, result.status());
    }

    @Test
    @DisplayName("Debe invocar todos los puertos en el orden correcto")
    void testProcessPayment_InvocationOrder() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.LOW));
        when(riskBureauPort.hasAccountRestrictions(anyString())).thenReturn(Uni.createFrom().item(false));
        when(coreBankingPort.reserveFunds(anyString(), any(BigDecimal.class))).thenReturn(Uni.createFrom().item(true));
        when(paymentGatewayPort.executePayment(any(Payment.class))).thenReturn(Uni.createFrom().item("PAY-OK"));

        processPaymentUseCase.execute(testPayment).await().indefinitely();

        var inOrder = inOrder(antiFraudPort, riskBureauPort, coreBankingPort, paymentGatewayPort);
        inOrder.verify(antiFraudPort).verifyFraudRisk(any(Payment.class));
        inOrder.verify(riskBureauPort).getAccountRiskLevel(anyString());
        inOrder.verify(riskBureauPort).hasAccountRestrictions(anyString());
        inOrder.verify(coreBankingPort).reserveFunds(anyString(), any(BigDecimal.class));
        inOrder.verify(paymentGatewayPort).executePayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe capturar y verificar el payment enviado a cada puerto")
    void testProcessPayment_PaymentPayloadCapture() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.LOW));
        when(riskBureauPort.hasAccountRestrictions(anyString())).thenReturn(Uni.createFrom().item(false));
        when(coreBankingPort.reserveFunds(anyString(), any(BigDecimal.class))).thenReturn(Uni.createFrom().item(true));
        when(paymentGatewayPort.executePayment(any(Payment.class))).thenReturn(Uni.createFrom().item("PAY-OK"));

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        processPaymentUseCase.execute(testPayment).await().indefinitely();

        verify(paymentGatewayPort).executePayment(paymentCaptor.capture());
        Payment capturedPayment = paymentCaptor.getValue();
        assertEquals(testPayment.sourceAccount(), capturedPayment.sourceAccount());
        assertEquals(testPayment.destinationAccount(), capturedPayment.destinationAccount());
        assertEquals(testPayment.amount(), capturedPayment.amount());
    }
}

// === ARCHIVO: src/test/java/com/financial/payments/infrastructure/adapters/AntiFraudAdapterTest.java ===
package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.model.Payment.PaymentStatus;
import com.financial.payments.domain.ports.AntiFraudPort;
import com.financial.payments.infrastructure.exceptions.AntiFraudServiceException;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class AntiFraudAdapterTest {

    @Inject
    AntiFraudAdapter antiFraudAdapter;

    private Payment createTestPayment(String sourceAccount, BigDecimal amount) {
        return new Payment(
            "TXN-TEST-" + System.currentTimeMillis(),
            sourceAccount,
            "ACC-DEST-001",
            amount,
            "USD",
            PaymentStatus.PENDING,
            LocalDateTime.now(),
            0,
            null
        );
    }

    @Test
    @DisplayName("Debe retornar true para pagos de bajo riesgo")
    void testVerifyFraudRisk_LowRisk_ReturnsTrue() {
        Payment lowRiskPayment = createTestPayment("ACC-NORMAL", new BigDecimal("500.00"));

        Uni<Boolean> result = antiFraudAdapter.verifyFraudRisk(lowRiskPayment);
        Boolean verified = result.await().indefinitely();

        assertTrue(verified);
    }

    @Test
    @DisplayName("Debe retornar false para pagos de alto monto que superan umbral")
    void testVerifyFraudRisk_HighAmount_ReturnsFalse() {
        Payment highAmountPayment = createTestPayment("ACC-NORMAL", new BigDecimal("50000.00"));

        Uni<Boolean> result = antiFraudAdapter.verifyFraudRisk(highAmountPayment);
        Boolean verified = result.await().indefinitely();

        assertFalse(verified);
    }

    @Test
    @DisplayName("Debe retornar score bajo para pagos normales")
    void testGetRiskScore_LowRiskPayment_ReturnsLowScore() {
        Payment normalPayment = createTestPayment("ACC-NORMAL", new BigDecimal("100.00"));

        Uni<Integer> result = antiFraudAdapter.getRiskScore(normalPayment);
        Integer score = result.await().indefinitely();

        assertNotNull(score);
        assertTrue(score < 30);
    }

    @Test
    @DisplayName("Debe retornar score alto para pagos de monto elevado")
    void testGetRiskScore_HighAmount_ReturnsHighScore() {
        Payment highAmountPayment = createTestAccountWithHighAmount();

        Uni<Integer> result = antiFraudAdapter.getRiskScore(highAmountPayment);
        Integer score = result.await().indefinitely();

        assertNotNull(score);
        assertTrue(score >= 70);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el servicio externo falla")
    void testVerifyFraudRisk_ServiceFailure_ThrowsException() {
        Payment payment = createTestPayment("ACC-ERROR", new BigDecimal("1000.00"));

        assertThrows(AntiFraudServiceException.class, () -> {
            antiFraudAdapter.verifyFraudRisk(payment).await().indefinitely();
        });
    }

    @Test
    @DisplayName("Debe lanzar excepción en getRiskScore cuando el servicio externo falla")
    void testGetRiskScore_ServiceFailure_ThrowsException() {
        Payment payment = createTestPayment("ACC-ERROR", new BigDecimal("1000.00"));

        assertThrows(AntiFraudServiceException.class, () -> {
            antiFraudAdapter.getRiskScore(payment).await().indefinitely();
        });
    }

    @Test
    @DisplayName("Debe simular latencia del servicio externo")
    void testVerifyFraudRisk_LatencySimulation() throws InterruptedException {
        Payment normalPayment = createTestPayment("ACC-LATENCY", new BigDecimal("200.00"));

        long startTime = System.currentTimeMillis();
        antiFraudAdapter.verifyFraudRisk(normalPayment).await().indefinitely();
        long duration = System.currentTimeMillis() - startTime;

        assertTrue(duration >= 50, "La latencia simulada debe ser al menos 50ms");
    }

    @Test
    @DisplayName("Debe manejar cuenta bloqueada correctamente")
    void testVerifyFraudRisk_BlockedAccount_ReturnsFalse() {
        Payment blockedAccountPayment = createTestPayment("ACC-BLOCKED", new BigDecimal("100.00"));

        Uni<Boolean> result = antiFraudAdapter.verifyFraudRisk(blockedAccountPayment);
        Boolean verified = result.await().indefinitely();

        assertFalse(verified);
    }

    @Test
    @DisplayName("Debe calcular score basado en múltiples factores de riesgo")
    void testGetRiskScore_MultipleRiskFactors_ReturnsCumulativeScore() {
        Payment multiRiskPayment = createTestPayment("ACC-NEW", new BigDecimal("15000.00"));

        Uni<Integer> result = antiFraudAdapter.getRiskScore(multiRiskPayment);
        Integer score = result.await().indefinitely();

        assertNotNull(score);
        assertTrue(score > 50, "El score debe ser alto por múltiples factores de riesgo");
    }

    @Test
    @DisplayName("Debe implementar el puerto AntiFraudPort correctamente")
    void testImplementsAntiFraudPort() {
        assertTrue(antiFraudAdapter instanceof AntiFraudPort);
    }

    private Payment createTestAccountWithHighAmount() {
        return new Payment(
            "TXN-HIGH-" + System.currentTimeMillis(),
            "ACC-HIGH-AMOUNT",
            "ACC-DEST-002",
            new BigDecimal("75000.00"),
            "USD",
            PaymentStatus.PENDING,
            LocalDateTime.now(),
            0,
            null
        );
    }
}
```
