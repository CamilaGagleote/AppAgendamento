package br.edu.fateczl.appagendamento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.edu.fateczl.appagendamento.controller.ConsultaController;
import br.edu.fateczl.appagendamento.controller.ExameController;
import br.edu.fateczl.appagendamento.model.Consulta;
import br.edu.fateczl.appagendamento.model.Exame;
import br.edu.fateczl.appagendamento.persistence.ConsultaDao;
import br.edu.fateczl.appagendamento.persistence.ExameDao;


public class ExameFragment extends Fragment {


    private View view;

    private TextView tvlistarEx;

    private EditText etNumeroEx, etTipoEx, etDataEx;

    private Button btnBuscarEx, btnAgendarEx, btnModificarEx, btnListarEx, btnExcluirEx;

    private ExameController eCont;

    public ExameFragment(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       view = inflater.inflate(R.layout.fragment_exame, container, false);
       tvlistarEx = view.findViewById(R.id.tvListarEx);
       tvlistarEx.setMovementMethod(new ScrollingMovementMethod());
       etNumeroEx = view.findViewById(R.id.etNumeroEx);
       etTipoEx = view.findViewById(R.id.etTipoEx);
       etDataEx = view.findViewById(R.id.etDataEx);
       btnBuscarEx = view.findViewById(R.id.btnBuscarEx);
       btnAgendarEx = view.findViewById(R.id.btnAgendarEx);
       btnModificarEx = view.findViewById(R.id.btnModificarEx);
       btnListarEx = view.findViewById(R.id.btnListarEx);
       btnExcluirEx = view.findViewById(R.id.btnExcluirEx);

        eCont = new ExameController(new ExameDao(view.getContext()));

        btnAgendarEx.setOnClickListener(op -> acaoAgendar());
        btnModificarEx.setOnClickListener(op -> acaoModificar());
        btnExcluirEx.setOnClickListener(op -> acaoExcluir());
        btnBuscarEx.setOnClickListener(op -> acaoBuscar());
        btnListarEx.setOnClickListener(op -> acaoListar());

       return view;
    }

    private void acaoAgendar() {
        Exame exame = montaExame();
        try {
            eCont.agendar(exame);
            Toast.makeText(view.getContext(), "Exame agendado com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();

    }

    private void acaoModificar() {
        Exame exame = montaExame();
        try {
            eCont.modificar(exame);
            Toast.makeText(view.getContext(), "Exame atualizado com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoExcluir() {
        Exame exame = montaExame();
        try {
            eCont.deletar(exame);
            Toast.makeText(view.getContext(), "Exame excluido com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        Exame exame = montaExame();
        try {
            exame = eCont.buscar(exame);
            if(exame.getTipoExame() != null){
                preencheCampos(exame);
            }else{
                Toast.makeText(view.getContext(),"Exame não encontrado", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void acaoListar() {
        try {
            List<Exame> exames = eCont.listar();
            StringBuffer buffer = new StringBuffer();
            for(Exame e : exames){
                buffer.append(e.toString() + "\n");
            }
            tvlistarEx.setText(buffer.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Exame montaExame(){
       Exame ex = new Exame();
        ex.setNumero(Integer.parseInt(etNumeroEx.getText().toString()));
        ex.setTipoExame(etTipoEx.getText().toString());
        String dataString = etDataEx.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = sdf.parse(dataString);
            ex.setData(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ex;
    }

    private void preencheCampos(Exame e){
        etNumeroEx.setText(String.valueOf(e.getNumero()));
        etTipoEx.setText(e.getTipoExame());
        etDataEx.setText(String.valueOf(e.getData()));
    }

    private void limpaCampos(){
        etNumeroEx.setText("");
        etTipoEx.setText("");
        etDataEx.setText("");
    }
}