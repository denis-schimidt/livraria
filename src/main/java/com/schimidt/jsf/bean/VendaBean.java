package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.modelo.Venda;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.Year;
import java.util.List;
import java.util.Random;

@Named
@ViewScoped
public class VendaBean implements Serializable {
    private static final long serialVersionUID = 3124911786637655200L;

    @Inject
    private DAO<Venda> dao;

    public BarChartModel getTodasAsVendas() {
        BarChartModel model = new BarChartModel();
        model.setAnimate(true);
        model.setShadow(true);

        List<ChartSeries> chartSeriesByYear = List.of(new ChartSeries(), new ChartSeries(), new ChartSeries());

        Random random = new Random(1234);

        dao.listaTodos()
                .forEach(venda -> {
                    Year anoAtual = Year.now();
                    Year anoVenda = Year.of(venda.getDataVenda().getYear());

                    int indice = anoAtual.compareTo(anoVenda);

                    ChartSeries chartSeries = chartSeriesByYear.get(indice);
                    chartSeries.setLabel(String.valueOf(anoVenda.getValue()));
                    chartSeries.set(venda.getLivro().getTitulo(), venda.getQuantidade());
                });

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Título");

        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Quantidade");

        model.setTitle("Vendas de livros");
        model.setLegendPosition("ne");

        chartSeriesByYear.forEach(model::addSeries);

        return model;
    }
}
