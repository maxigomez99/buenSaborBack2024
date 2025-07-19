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
    "precioVenta": 500.0,
    "categoria": {
      "id": 1,
      "denominacion": "Insumos básicos"
    },
    "unidadMedida": {
      "id": 1,
      "denominacion": "Kg"
    },
    "stockActual": 50.0,
    "stockMinimo": 10.0,
    "stockMaximo": 100.0,
    "precioCompra": 400.0,
    "esParaCompra": true,
    "esParaVenta": false
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
  "precioVenta": 500.0,
  "categoria": {
    "id": 1,
    "denominacion": "Insumos básicos"
  },
  "unidadMedida": {
    "id": 1,
    "denominacion": "Kg"
  },
  "stockActual": 50.0,
  "stockMinimo": 10.0,
  "stockMaximo": 100.0,
  "precioCompra": 400.0,
  "esParaCompra": true,
  "esParaVenta": false
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
  "denominacion": "Queso Muzzarella",
  "precioVenta": 800.0,
  "categoria": {
    "id": 2
  },
  "unidadMedida": {
    "id": 1
  },
  "stockActual": 30.0,
  "stockMinimo": 5.0,
  "stockMaximo": 50.0,
  "precioCompra": 700.0,
  "esParaCompra": true,
  "esParaVenta": true
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
  "precioVenta": 900.0,
  "categoria": {
    "id": 2
  },
  "unidadMedida": {
    "id": 1
  },
  "stockActual": 30.0,
  "stockMinimo": 5.0,
  "stockMaximo": 50.0,
  "precioCompra": 800.0,
  "esParaCompra": true,
  "esParaVenta": true
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

#### Obtener todas las unidades de medida

```
GET /api/unidades-medida
```

#### Obtener una unidad de medida por ID

```
GET /api/unidades-medida/{id}
```

#### Crear una nueva unidad de medida

```
POST /api/unidades-medida
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "Litro",
  "abreviatura": "L",
  "activo": true
}
```

#### Actualizar una unidad de medida

```
PUT /api/unidades-medida/{id}
```

#### Eliminar una unidad de medida

```
DELETE /api/unidades-medida/{id}
```

## Promociones

#### Obtener todas las promociones

```
GET /api/promociones
```

#### Obtener una promoción por ID

```
GET /api/promociones/{id}
```

#### Crear una nueva promoción

```
POST /api/promociones
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "2x1 en Pizzas",
  "descripcion": "Lleva 2 pizzas y paga 1",
  "fechaDesde": "2025-07-20",
  "fechaHasta": "2025-08-20",
  "activo": true,
  "tipoPromocion": {
    "id": 1
  },
  "detalles": [
    {
      "articulo": {
        "id": 5
      },
      "cantidad": 2
    }
  ]
}
```

#### Actualizar una promoción

```
PUT /api/promociones/{id}
```

#### Eliminar una promoción

```
DELETE /api/promociones/{id}
```

## Tipos de Promoción

#### Obtener todos los tipos de promoción

```
GET /api/tipos-promocion
```

#### Obtener un tipo de promoción por ID

```
GET /api/tipos-promocion/{id}
```

#### Crear un nuevo tipo de promoción

```
POST /api/tipos-promocion
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "Descuento porcentaje",
  "activo": true
}
```

#### Actualizar un tipo de promoción

```
PUT /api/tipos-promocion/{id}
```

#### Eliminar un tipo de promoción

```
DELETE /api/tipos-promocion/{id}
```

## Imágenes de Promoción

#### Obtener todas las imágenes de promoción

```
GET /api/imagenes-promocion
```

#### Obtener una imagen de promoción por ID

```
GET /api/imagenes-promocion/{id}
```

#### Crear una nueva imagen de promoción

```
POST /api/imagenes-promocion
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "Imagen Promo 2x1",
  "promocion": {
    "id": 1
  }
}
```

#### Actualizar una imagen de promoción

```
PUT /api/imagenes-promocion/{id}
```

#### Eliminar una imagen de promoción

```
DELETE /api/imagenes-promocion/{id}
```

## Localidades

#### Obtener todas las localidades

```
GET /api/localidades
```

#### Obtener una localidad por ID

```
GET /api/localidades/{id}
```

#### Crear una nueva localidad

```
POST /api/localidades
```

**Cuerpo de la solicitud:**
```json
{
  "denominacion": "Godoy Cruz",
  "activo": true
}
```

#### Actualizar una localidad

```
PUT /api/localidades/{id}
```

#### Eliminar una localidad

```
DELETE /api/localidades/{id}
```

## Empresa

#### Obtener información de la empresa

```
GET /api/empresa
```

#### Obtener información de la empresa por ID

```
GET /api/empresa/{id}
```

#### Crear o actualizar información de la empresa

```
POST /api/empresa
```

**Cuerpo de la solicitud:**
```json
{
  "nombre": "Buen Sabor",
  "email": "info@buensabor.com",
  "telefono": "+54 261 123 4567",
  "domicilio": "Av. San Martín 123",
  "localidad": {
    "id": 1
  },
  "horaApertura": "11:00",
  "horaCierre": "23:00"
}
```

#### Actualizar información de la empresa

```
PUT /api/empresa/{id}
```

#### Eliminar información de la empresa

```
DELETE /api/empresa/{id}
```
