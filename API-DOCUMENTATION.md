# Documentación API BuenSabor

Esta documentación describe los endpoints disponibles para interactuar con la API del sistema BuenSabor.

## Información General

- **URL Base**: `http://localhost:8080` (por defecto)
- **Formato**: Todas las solicitudes y respuestas utilizan formato JSON
- **Autenticación**: No especificada en la documentación actual

## Códigos de Estado HTTP

- `200 OK`: Solicitud procesada correctamente
- `201 Created`: Recurso creado correctamente
- `204 No Content`: Solicitud procesada correctamente, no hay contenido para devolver
- `400 Bad Request`: Error en la solicitud
- `404 Not Found`: Recurso no encontrado
- `500 Internal Server Error`: Error interno del servidor

## Artículos

### ArticuloInsumo

#### Obtener todos los insumos

```
GET /api/articulos-insumo
```

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "denominacion": "Harina",
    "precioCompra": 400,
    "precioVenta": 500,
    "stockActual": 50,
    "stockMinimo": 10,
    "esParaElaborar": true,
    "unidadMedida": {
      "id": 1,
      "denominacion": "Kg",
      "abreviatura": "kg"
    }
  }
]
```

#### Obtener un insumo por ID

```
GET /api/articulos-insumo/{id}
```

**Respuesta exitosa:**
```json
{
  "id": 1,
  "denominacion": "Harina",
  "precioCompra": 400,
  "precioVenta": 500,
  "stockActual": 50,
  "stockMinimo": 10,
  "esParaElaborar": true,
  "unidadMedida": {
    "id": 1,
    "denominacion": "Kg",
    "abreviatura": "kg"
  }
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "error": "No se encontró el insumo"
}
```

#### Obtener insumos por categoría

```
GET /api/articulos-insumo/categoria/{categoriaId}
```

#### Crear un nuevo insumo

```
POST /api/articulos-insumo
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "molida",
  "precioCompra": 500,
  "precioVenta": 750,
  "stockActual": 20,
  "stockMinimo": 5,
  "esParaElaborar": true,
  "unidadMedida": {
    "id": 1
  }
}
```

#### Actualizar un insumo

```
PUT /api/articulos-insumo/{id}
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "Queso Muzzarella Premium",
  "precioCompra": 800,
  "precioVenta": 900,
  "stockActual": 30,
  "stockMinimo": 5,
  "esParaElaborar": true,
  "unidadMedida": {
    "id": 1
  }
}
```

#### Eliminar un insumo

```
DELETE /api/articulos-insumo/{id}
```

### ArticuloManufacturado

#### Obtener todos los productos manufacturados

```
GET /api/articulos-manufacturados
```

#### Obtener un producto manufacturado por ID

```
GET /api/articulos-manufacturados/{id}
```

#### Obtener productos manufacturados por categoría

```
GET /api/articulos-manufacturados/categoria/{categoriaId}
```

#### Crear un nuevo producto manufacturado

```
POST /api/articulos-manufacturados
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "Pizza Muzzarella",
  "precioVenta": 2500.0,
  "categoria": {
    "id": 3
  },
  "unidadMedida": {
    "id": 2
  },
  "tiempoEstimadoCocina": 20,
  "preparacion": "Estirar la masa, agregar salsa y queso, hornear",
  "detalles": [
    {
      "cantidad": 0.3,
      "articulo": {
        "id": 1
      }
    },
    {
      "cantidad": 0.2,
      "articulo": {
        "id": 2
      }
    }
  ]
}
```

#### Actualizar un producto manufacturado

```
PUT /api/articulos-manufacturados/{id}
```

#### Eliminar un producto manufacturado

```
DELETE /api/articulos-manufacturados/{id}
```

## Categorías

#### Obtener todas las categorías

```
GET /api/categorias
```

#### Obtener una categoría por ID

```
GET /api/categorias/{id}
```

#### Crear una nueva categoría

```
POST /api/categorias
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "Bebidas",
  "activo": true
}
```

#### Actualizar una categoría

```
PUT /api/categorias/{id}
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "Bebidas alcohólicas",
  "activo": true
}
```

#### Eliminar una categoría

```
DELETE /api/categorias/{id}
```

## Unidades de Medida

#### Obtener todas las unidades de medida activas

```
GET /api/unidades-medida
```

**Descripción:** Retorna solo las unidades de medida no eliminadas (campo `eliminado = false`).

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "denominacion": "Kilogramo",
    "abreviatura": "Kg",
    "eliminado": false
  }
]
```

#### Obtener TODAS las unidades de medida (incluyendo eliminadas)

```
GET /api/unidades-medida/all
```

**Descripción:** Retorna **todas** las unidades de medida de la base de datos, incluyendo las marcadas como eliminadas (campo `eliminado = true`).

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "denominacion": "Kilogramo",
    "abreviatura": "Kg",
    "eliminado": false
  },
  {
    "id": 2,
    "denominacion": "Litro",
    "abreviatura": "L", 
    "eliminado": true
  }
]
```

#### Obtener solo unidades de medida activas

```
GET /api/unidades-medida/activas
```

**Descripción:** Endpoint específico para obtener solo las unidades de medida activas (no eliminadas).

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "denominacion": "Kilogramo",
    "abreviatura": "Kg",
    "eliminado": false
  }
]
```

#### Obtener una unidad de medida por ID

```
GET /api/unidades-medida/{id}
```

**Respuesta exitosa:**
```json
{
  "id": 1,
  "denominacion": "Kilogramo",
  "abreviatura": "Kg",
  "eliminado": false
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "error": "No se encontró la unidad de medida"
}
```

#### Crear una nueva unidad de medida

```
POST /api/unidades-medida
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "Litro",
  "abreviatura": "L"
}
```

**Validaciones:**
- La denominación debe ser única (no distingue mayúsculas/minúsculas)
- La abreviatura debe ser única (no distingue mayúsculas/minúsculas)

**Respuesta exitosa (201 Created):**
```json
{
  "id": 1,
  "denominacion": "Litro",
  "abreviatura": "L",
  "eliminado": false
}
```

**Respuestas de error (400 Bad Request):**
```json
{
  "error": "Ya existe una unidad de medida con la denominación: Litro"
}
```
```json
{
  "error": "Ya existe una unidad de medida con la abreviatura: L"
}
```

#### Actualizar una unidad de medida

```
PUT /api/unidades-medida/{id}
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "Kilogramo",
  "abreviatura": "kg"
}
```

**Validaciones:**
- La denominación debe ser única (no distingue mayúsculas/minúsculas)
- La abreviatura debe ser única (no distingue mayúsculas/minúsculas)
- No se pueden repetir con otras unidades de medida existentes

**Respuesta exitosa:**
```json
{
  "id": 1,
  "denominacion": "Kilogramo",
  "abreviatura": "kg",
  "eliminado": false
}
```

**Respuestas de error (400 Bad Request):**
```json
{
  "error": "Ya existe otra unidad de medida con la denominación: Kilogramo"
}
```
```json
{
  "error": "Ya existe otra unidad de medida con la abreviatura: kg"
}
```

#### Eliminar una unidad de medida

```
DELETE /api/unidades-medida/{id}
```

**Descripción:** Realiza un **eliminado lógico** o físico dependiendo del uso de la unidad de medida.

**Comportamiento:**
- Si la unidad de medida está siendo utilizada por artículos: Solo marca como eliminada (`eliminado = true`)
- Si NO está siendo utilizada: Elimina físicamente el registro de la base de datos

**Respuesta exitosa (204 No Content):** Sin contenido

**Respuestas de error:**
```json
{
  "error": "No se encontró la unidad de medida"
}
```
```json
{
  "error": "Error al eliminar la unidad de medida: [detalle del error]"
}
```

#### Activar/Desactivar una unidad de medida

```
PATCH /api/unidades-medida/{id}/toggle-estado
```

**Descripción:** Alterna el estado de la unidad de medida entre activa (eliminado = false) y desactivada (eliminado = true) con una sola llamada. Si está activa la desactiva, si está desactivada la activa.

**Respuesta exitosa:**
```json
{
  "mensaje": "Unidad de medida activada exitosamente",
  "unidadMedida": {
    "id": 1,
    "denominacion": "Kilogramo",
    "abreviatura": "Kg",
    "eliminado": false
  }
}
```

**Respuesta cuando se desactiva:**
```json
{
  "mensaje": "Unidad de medida desactivada exitosamente",
  "unidadMedida": {
    "id": 1,
    "denominacion": "Kilogramo", 
    "abreviatura": "Kg",
    "eliminado": true
  }
}
```
