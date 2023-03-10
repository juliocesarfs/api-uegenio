package br.ueg.madamestore.application.model;

import br.ueg.madamestore.application.configuration.Constante;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.enums.converter.StatusEsperaConverter;
import br.ueg.madamestore.application.enums.converter.StatusVendidoConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "TBL_VENDA", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_VENDA", sequenceName = "TBL_S_VENDA", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data
class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_VENDA")
    @Column(name = "ID_VENDA", nullable = false)
    private Long id;


    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    private Cliente cliente;



    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "venda", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemVenda> itemVenda;

    @Column(name="VALORTOTAL", nullable = false)
    private Double valorTotal;

    @Column(name = "DATA_VENDA",nullable = false)
    private LocalDate dataVenda;

    @Convert(converter = StatusVendidoConverter.class)
    @Column(name = "STATUSVENDIDO", length = 1)
    private StatusVendido statusVendido;

    @Convert(converter = StatusEsperaConverter.class)
    @Column(name = "STATUSESPERA", length = 1)
    private StatusEspera statusEspera;


}
