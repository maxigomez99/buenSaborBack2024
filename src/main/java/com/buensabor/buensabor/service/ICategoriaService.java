package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.Categoria;
import com.buensabor.buensabor.dto.categoria.CategoriaDto;
import com.buensabor.buensabor.dto.categoria.SubCategoriaListaDto;



import java.util.Set;

public interface ICategoriaService {


    public Categoria cargar(Categoria categoria) throws Exception;
    public Categoria actualizarCategoriaPadre(Long id, Categoria categoria) throws Exception;
    public Set<Categoria> lista()throws Exception;
    public CategoriaDto buscar(Long id)throws Exception;
    public boolean eliminar(Long id)throws Exception;
    public boolean eliminarSubCategoria(Long idCategoria, Long idSubCategoria) throws Exception;
    public Set<SubCategoriaListaDto> obtenerSubCategorias(Long idCategoriaPadre) throws Exception;
    public Set<Categoria> obtenerCategoriasConSubCategorias() throws Exception;
    public Categoria agregarSubCategoria(Long id, Categoria subCategoria) throws Exception;
    //public Categoria agregarArticulo(Long id, Articulo articulo) throws Exception;
    public Categoria agregarArticulo(Long idCategoria, Long idArticulo) throws Exception;
    public Categoria actualizarSubCategoria(Long idSubCategoria, Categoria nuevaSubCategoria) throws Exception;
    public Categoria eliminarArticuloDeSubCategoria(Long idSubCategoria, Long idArticulo) throws Exception;
    public boolean reactivate(Long id) throws Exception;
    public Set<CategoriaDto> traerTodo() throws Exception;
    public Categoria Actualizar(long id, Categoria categoria) throws Exception;
    public Set<CategoriaDto> traerCategoriaPadre(Long sucursalId) throws Exception;
    public boolean tieneSubCategorias(Long categoriaId) throws Exception;


}