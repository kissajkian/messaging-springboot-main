package br.com.santander.domain.usecase;

import br.com.santander.app.dto.Retry;

public interface RetryUseCase {

    public void execute(Retry retry);
}
