package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.dao.JPAUtil;
import com.schimidt.jsf.modelo.Livro;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import java.util.Random;

@ManagedBean
@ViewScoped
public class VendaBean {

    public BarChartModel getTodasAsVendas() {
        BarChartModel model = new BarChartModel();
        model.setAnimate(true);
        model.setShadow(true);

        ChartSeries seriesLivrosPenultimoAno = new ChartSeries();
        ChartSeries seriesLivrosAnoAtual = new ChartSeries();

        EntityManager entityManager = JPAUtil.newEntityManager();
        DAO<Livro> dao = new DAO<>(Livro.class, entityManager);

        Random random = new Random(1234);

        dao.listaTodos()
            .forEach(livro -> {
                seriesLivrosAnoAtual.set(livro.getTitulo(), random.nextInt(10000));
                seriesLivrosAnoAtual.setLabel("2017");

                seriesLivrosPenultimoAno.set(livro.getTitulo(), random.nextInt(10000));
                seriesLivrosPenultimoAno.setLabel("2016");
            });

        // pegando o eixo X do gráfico e setando o título do mesmo
        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Título");

        // pegando o eixo Y do gráfico e setando o título do mesmo
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Quantidade");

        entityManager.close();
        model.setTitle("Vendas de livros");
        model.setLegendPosition("ne");

        model.addSeries(seriesLivrosAnoAtual);
        model.addSeries(seriesLivrosPenultimoAno);

        return model;
    }
}
