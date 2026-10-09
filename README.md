# Manejo de Errores y Reintentos en un Sistema de Pagos

En un sistema de procesamiento de pagos de una entidad financiera, el servicio de pagos debe manejar los errores y reintentos de manera eficiente para garantizar la correcta transacción. El sistema interactúa con múltiples actores: el originador de créditos, el motor antifraude, el buró de riesgos, el core bancario y el gateway de pagos. El objetivo es asegurar que los pagos se realicen correctamente, a pesar de posibles fallos temporales en los servicios externos. Los umbrales del dominio incluyen un throughput de 1 500 solicitudes por segundo en hora pico y una latencia máxima de 2 segundos para la confirmación de un pago.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | E2E technologiesWork 202610091709 |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Implementación del Servicio de Pagos

**Objetivo:** Crear un servicio que acepte solicitudes de pago y las procese correctamente.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- El servicio debe recibir solicitudes de pago con los detalles necesarios (monto, cuenta origen, cuenta destino).
- El servicio debe interactuar con el motor antifraude para verificar la transacción.
- El servicio debe comunicarse con el buró de riesgos para obtener la calificación crediticia del pagador.
- El servicio debe enviar la solicitud al core bancario para la ejecución del pago.
- El servicio debe manejar los errores y reintentos en caso de fallos temporales en los servicios externos.

**Entregable:** Servicio de pagos básico que acepta y procesa solicitudes de pago.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los posibles fallos en cada uno de los servicios externos y cómo manejarlos.
- Piensa en cómo asegurar la idempotencia de las solicitudes de pago.

</details>

### Fase 2: Manejo de Errores y Reintentos

**Objetivo:** Mejorar el servicio para manejar errores y reintentos de manera eficiente.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Implementa una estrategia de reintentos para los servicios externos que fallen temporalmente.
- Maneja los errores de manera que el servicio pueda recuperarse y continuar operando.
- Asegura que los reintentos no causen duplicados en las transacciones.

**Entregable:** Servicio de pagos mejorado que maneja errores y reintentos de manera eficiente.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el uso de colas de mensajes para los reintentos.
- Piensa en cómo detectar y manejar los duplicados en las transacciones.

</details>

### Fase 3: Optimización y Refactorización

**Objetivo:** Optimizar y refactorizar el servicio para mejorar su rendimiento y mantenibilidad.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Optimiza el servicio para manejar un throughput de 1 500 solicitudes por segundo.
- Refactoriza el código para mejorar su legibilidad y mantenibilidad.
- Asegura que el servicio cumpla con la latencia máxima de 2 segundos para la confirmación de un pago.

**Entregable:** Servicio de pagos optimizado y refactorizado que cumple con los requisitos de rendimiento y latencia.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el uso de técnicas de paralelismo y asincronía para mejorar el rendimiento.
- Piensa en cómo modularizar el código para mejorar su legibilidad y mantenibilidad.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es el manejo de errores y reintentos en un sistema de pagos?
- **paraQueSirve**: ¿Para qué sirve el manejo de errores y reintentos en un sistema de pagos?
- **comoSeUsa**: ¿Cómo se usa el manejo de errores y reintentos en un sistema de pagos?
- **erroresComunes**: ¿Cuáles son los errores comunes que pueden ocurrir en un sistema de pagos y cómo se manejan?
- **queDecisionesImplica**: ¿Qué decisiones implica el manejo de errores y reintentos en un sistema de pagos?

## Criterios de Evaluacion

- Implementación de un servicio de pagos que acepte y procese solicitudes de pago.
- Manejo eficiente de errores y reintentos en el servicio de pagos.
- Optimización y refactorización del servicio de pagos para mejorar su rendimiento y mantenibilidad.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
