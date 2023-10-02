package es.urjc.tfm.scheduly.domain.usecases;

import es.urjc.tfm.scheduly.domain.ports.ComunicationService;
import es.urjc.tfm.scheduly.domain.ports.ComunicationUseCase;

public class ComunicationUseCaseImpl implements ComunicationUseCase{

	private ComunicationService comunicationService;

	public ComunicationUseCaseImpl(ComunicationService comunicationService) {
		this.comunicationService = comunicationService;
	}

	@Override
	public void sendMessage(String message) throws Exception{
		this.comunicationService.sendMessage(message);
	}

}