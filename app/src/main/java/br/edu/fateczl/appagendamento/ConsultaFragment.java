package br.edu.fateczl.appagendamento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.edu.fateczl.appagendamento.controller.ConsultaController;
import br.edu.fateczl.appagendamento.model.Consulta;
import br.edu.fateczl.appagendamento.persistence.ConsultaDao;


public class ConsultaFragment extends Fragment {

    private View view;

    private TextView tvlistarCons;

    private EditText etNumeroCons, etEspecCons, etDataCons;

    private Spinner spMedicoCons;

    private Button btnBuscarCons, btnAgendarCons, btnModificarCons, btnListarCons, btnExcluirCons;

    private ConsultaController cCont;

    public ConsultaFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_consulta, container, false);

        tvlistarCons = view.findViewById(R.id.tvListarCons);
        tvlistarCons.setMovementMethod(new ScrollingMovementMethod());
        etNumeroCons = view.findViewById(R.id.etNumeroCons);
        etEspecCons = view.findViewById(R.id.etEspecCons);
        etDataCons = view.findViewById(R.id.etDataCons);
        spMedicoCons = view.findViewById(R.id.spMedicoCons);
        btnBuscarCons = view.findViewById(R.id.btnBuscarCons);
        btnAgendarCons = view.findViewById(R.id.btnAgendarCons);
        btnModificarCons = view.findViewById(R.id.btnModificarCons);
        btnListarCons = view.findViewById(R.id.btnListarCons);
        btnExcluirCons = view.findViewById(R.id.btnExcluirCons);

        cCont = new ConsultaController(new ConsultaDao(view.getContext()));
        preencheSpinner();

        btnAgendarCons.setOnClickListener(op -> acaoAgendar());
        btnModificarCons.setOnClickListener(op -> acaoModificar());
        btnExcluirCons.setOnClickListener(op -> acaoExcluir());
        btnBuscarCons.setOnClickListener(op -> acaoBuscar());
        btnListarCons.setOnClickListener(op -> acaoListar());

        return view;
    }

    private void preencheSpinner() {

        String[] medicos = {"Paula", "Robson", "Eduarda", "Jose"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, medicos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMedicoCons.setAdapter(adapter);


    }

    private void acaoListar() {
        try {
            List<Consulta> consultas = cCont.listar();
            StringBuffer buffer = new StringBuffer();
            for(Consulta c : consultas){
                buffer.append(c.toString() + "\n");
            }
            tvlistarCons.setText(buffer.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void acaoBuscar() {
        Consulta consulta = montaConsulta();
        try {
            consulta = cCont.buscar(consulta);
            if(consulta.getEspecialidade() != null){
                preencheCampos(consulta);
            }else{
                Toast.makeText(view.getContext(),"Consulta não encontrada", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    private void acaoModificar() {
        Consulta consulta = montaConsulta();
        try {
            cCont.modificar(consulta);
            Toast.makeText(view.getContext(), "Consulta atualizado com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoExcluir() {
        Consulta consulta = montaConsulta();
        try {
            cCont.deletar(consulta);
            Toast.makeText(view.getContext(), "Consulta excluida com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoAgendar() {
        Consulta consulta = montaConsulta();
        try {
            cCont.agendar(consulta);
            Toast.makeText(view.getContext(), "Consulta agendada com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();

    }

    private Consulta montaConsulta(){
        Consulta c = new Consulta();
        c.setNumero(Integer.parseInt(etNumeroCons.getText().toString()));
        c.setEspecialidade(etEspecCons.getText().toString());
        String dataString = etDataCons.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = sdf.parse(dataString);
            c.setData(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return c;
    }

    private void preencheCampos(Consulta c){
        etNumeroCons.setText(String.valueOf(c.getNumero()));
        etEspecCons.setText(c.getEspecialidade());
        etDataCons.setText(String.valueOf(c.getData()));
    }

    private void limpaCampos(){
        etNumeroCons.setText("");
        etEspecCons.setText("");
        etDataCons.setText("");
    }

}