package com.pucmgcoreu.contatoadd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nome;
        private final TextView email;
        private final TextView telefoneFixo;
        private final TextView telefoneCelular;
        private final ImageView deletar;
        private final TextView numero;

        private ViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome);
            email = itemView.findViewById(R.id.email);
            telefoneFixo = itemView.findViewById(R.id.telefone_fixo);
            telefoneCelular = itemView.findViewById(R.id.telefone_celular);
            deletar = itemView.findViewById(R.id.delete);
            numero = itemView.findViewById(R.id.numero_pedido);
        }
    }

    private final LayoutInflater mInflater;
    private List<Pessoa> mRequests;
    private Context context;
    private PessoaViewModel viewModel;
    PessoaAdapter(Context context, PessoaViewModel viewModel, List<Pessoa> list) {
        mInflater = LayoutInflater.from(context);
        this.viewModel = viewModel;
        this.context = context;
        this.mRequests = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.pessoa_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mRequests != null) {
            final Pessoa item = mRequests.get(position);
            holder.numero.setText(item.getRid()+"");
            holder.nome.setText(item.getNome());
            holder.email.setText(item.getEmail());
            holder.telefoneCelular.setText(item.getTelefoneCelular());
            holder.telefoneFixo.setText(item.getTelefoneFixo());

            holder.deletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.delete(item);
                    Toast.makeText(context, item.getNome() + " deletado", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    void setWords(List<Pessoa> r){
        mRequests = r;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mRequests != null)
            return mRequests.size();
        else return 0;
    }

}