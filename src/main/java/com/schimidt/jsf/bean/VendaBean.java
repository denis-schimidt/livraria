package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.modelo.Livro;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Random;

@Named
@ViewScoped
public class VendaBean implements Serializable {
    private static final long serialVersionUID = 3124911786637655200L;

    @Inject
    private DAO<Livro> dao;

    public BarChartModel getTodasAsVendas() {
        BarChartModel model = new BarChartModel();
        model.setAnimate(true);
        model.setShadow(true);

        ChartSeries seriesLivrosPenultimoAno = new ChartSeries();
        ChartSeries seriesLivrosAnoAtual = new ChartSeries();

        Random random = new Random(1234);

        dao.listaTodos()
            .forEach(livro -> {
                seriesLivrosAnoAtual.set(livro.getTitulo(), random.nextInt(10000));
                seriesLivrosAnoAtual.setLabel("2017");

                seriesLivrosPenultimoAno.set(livro.getTitulo(), random.nextInt(10000));
                seriesLivrosPenultimoAno.setLabel("2016");
            });

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Título");

        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Quantidade");

        model.setTitle("Vendas de livros");
        model.setLegendPosition("ne");

        model.addSeries(seriesLivrosAnoAtual);
        model.addSeries(seriesLivrosPenultimoAno);

        return model;
    }
}
