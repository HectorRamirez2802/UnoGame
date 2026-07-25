# UNO (Java Swing)

Implementación del clásico juego de cartas **UNO** hecha en **Java puro** (sin bases de datos ni frameworks externos), usando `javax.swing` para la interfaz gráfica y `javax.sound.sampled` para los efectos de sonido y música.

Soporta partidas de **2, 3 o 4 jugadores** en el mismo equipo (hot-seat / pasa y juega).

## 🎮 Características

- Menú principal con selección de número de jugadores (2, 3 o 4).
- Reparto automático de 7 cartas por jugador al iniciar la partida.
- Mazo completo de UNO: cartas numéricas (0-9) en 4 colores, `+2`, `+4`, `Bloqueo`, `Reversa` y `Cambia Color`.
- Cambio de dirección del turno (sentido horario / antihorario) al jugar una carta "Reversa".
- Lógica para robar cartas cuando no se tiene jugada válida.
- Pantalla de victoria con estadísticas por jugador:
  - Cartas jugadas
  - Cantidad de `+2` jugados
  - Cantidad de bloqueos jugados
  - Cantidad de reversas jugadas
- Música de fondo distinta según el número de jugadores, y música especial al ganar.
- Efectos de sonido al jugar cartas y al seleccionar opciones del menú.

## 🗂️ Estructura del proyecto

```
UNO/
├── build/              # Generado automáticamente al compilar (no subir a Git)
├── nbproject/          # Configuración del proyecto en NetBeans
├── src/
│   └── uno/
│       ├── Main.java        # Punto de entrada del programa
│       ├── MainTitle.java   # Ventana de menú principal
│       ├── Gameplay.java    # Lógica principal del juego y la ventana de partida
│       ├── Carta.java       # Modelo de una carta (color, tipo, valor, ruta de imagen)
│       ├── Jugador.java     # Modelo de un jugador (nombre, mano de cartas, estadísticas)
│       └── Sound.java       # Manejo de música y efectos de sonido
├── test/               # Pruebas del proyecto
├── build.xml           # Script de compilación (Ant)
└── manifest.mf         # Manifiesto de la aplicación
```

## 🧩 Clases principales

| Clase | Responsabilidad |
|---|---|
| `Main` | Arranca la aplicación mostrando `MainTitle`. |
| `MainTitle` | Ventana de inicio: logo, botón de jugar/salir y selección de 2/3/4 jugadores. |
| `Gameplay` | Contiene toda la lógica de la partida: reparto, turnos, validación de jugadas, cartas especiales, animaciones y estadísticas finales. |
| `Carta` | Representa una carta individual (dirección de imagen, color, tipo y/o valor numérico). |
| `Jugador` | Representa a un jugador: su nombre, su mano (`ArrayList<Carta>`) y contadores de estadísticas. |
| `Sound` | Utilidad para cargar, reproducir, loopear y detener archivos de audio (`.wav`). |

## ▶️ Requisitos

- **JDK 8 o superior** (usa `javax.swing` y `javax.sound.sampled`, incluidos en el JDK estándar).
- **Apache Ant** (opcional, si se quiere compilar vía `build.xml`) o **NetBeans IDE**, ya que el proyecto incluye la carpeta `nbproject`.

## 🚀 Cómo ejecutarlo

### Opción 1: Desde NetBeans
1. Abrir NetBeans → `File > Open Project` → seleccionar la carpeta raíz del proyecto.
2. Ejecutar (`Run > Run Project` o `F6`).

### Opción 2: Desde línea de comandos con Ant
```bash
ant run
```

### Opción 3: Compilando manualmente
```bash
cd src
javac uno/*.java -d ../build/classes
java -cp ../build/classes uno.Main
```

> ⚠️ **Importante:** las rutas a las imágenes y sonidos están escritas de forma relativa (`src/Assets/...`, `/Sound/...`), por lo que el programa debe ejecutarse desde la raíz del proyecto para que los recursos se carguen correctamente.

## 📁 Recursos necesarios

El juego espera encontrar los siguientes recursos dentro de `src`:
- `src/Assets/` → imágenes de fondo, botones, logo, íconos de cartas boca abajo, etc.
- `src/CartasUno/` → imágenes de cada carta del mazo (numéricas y especiales).
- `src/Sound/` → archivos `.wav` de música y efectos.

Si el repositorio no incluye estos assets, el juego no arrancará correctamente por errores de recursos faltantes.

## 📝 Notas de desarrollo

- Existen comentarios en el código original que no fueron removidos, la gran mayoria indica posibles bugs o errores sin embargo todo esto fue corregido en su momento. En
caso de encontrar algun bug no dude en comentarlo.

## 📄 Licencia

Proyecto de uso educativo/personal. Ajusta esta sección si planeas darle una licencia específica (MIT, GPL, etc.).
