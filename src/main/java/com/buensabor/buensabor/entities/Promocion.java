package com.buensabor.buensabor.entities;

    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.SuperBuilder;

    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.util.List;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    @SuperBuilder
    public class Promocion extends Base {

        private String denominacion;

        @Column(name = "fecha_desde")
        private LocalDate fechaDesde;

        @Column(name = "fecha_hasta")
        private LocalDate fechaHasta;

        @Column(name = "hora_desde")
        private LocalTime horaDesde;

        @Column(name = "hora_hasta")
        private LocalTime horaHasta;

        private String descDescuento;

        @Column(name = "precio_promo")
        private Double precioPromo;

        @ManyToOne
        @JoinColumn(name = "tipo_promocion_id")
        private TipoPromocion tipoPromocion;

        @ManyToOne
        @JoinColumn(name = "empresa_id")
        private Empresa empresa;

        @OneToMany(mappedBy = "promocion")
        private List<PromocionDetalle> promocionDetalles;

        @OneToMany(mappedBy = "promocion")
        private List<ImagenPromocion> imagenes;
    }