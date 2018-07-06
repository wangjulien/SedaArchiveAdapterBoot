package com.telino.avp.seda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.telino.avp.seda.dto.AcknowledgementTypeDto;
import com.telino.avp.seda.dto.ArchiveDeliveryRequestTypeDto;
import com.telino.avp.seda.dto.ArchiveRestitutionRequestReplyTypeDto;
import com.telino.avp.seda.dto.ArchiveRestitutionRequestTypeDto;
import com.telino.avp.seda.dto.ArchiveTransferRequestTypeDto;
import com.telino.avp.seda.dto.ArchiveTransferTypeDto;
import com.telino.avp.seda.dto.AuthorizationControlAuthorityRequestReplyTypeDto;
import com.telino.avp.seda.dto.AuthorizationOriginatingAgencyRequestReplyTypeDto;
import com.telino.avp.seda.v1.types.AcknowledgementType;
import com.telino.avp.seda.v1.types.ArchiveDeliveryRequestReplyType;
import com.telino.avp.seda.v1.types.ArchiveDestructionNotificationType;
import com.telino.avp.seda.v1.types.ArchiveModificationNotificationType;
import com.telino.avp.seda.v1.types.ArchiveRestitutionRequestReplyType;
import com.telino.avp.seda.v1.types.ArchiveRestitutionRequestType;
import com.telino.avp.seda.v1.types.ArchiveRestitutionType;
import com.telino.avp.seda.v1.types.ArchiveTransferReplyType;
import com.telino.avp.seda.v1.types.ArchiveTransferRequestReplyType;
import com.telino.avp.seda.v1.types.AuthorizationControlAuthorityRequestType;
import com.telino.avp.seda.v1.types.AuthorizationOriginatingAgencyRequestType;

@Service
public class SedaServiceImpl implements SedaService {

	private String clientUrl;

	private String avpServerUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public AcknowledgementTypeDto requireArchiveTransfer(ArchiveTransferRequestTypeDto archiveTransferRequestDto) {

		return restTemplate.postForObject(avpServerUrl, archiveTransferRequestDto, AcknowledgementTypeDto.class);
	}

	@Override
	public AcknowledgementType replyArchiveTransferRequest(
			ArchiveTransferRequestReplyType archiveTransferRequestReplyType) {
		// TODO Auto-generated method stub

		restTemplate.postForObject(clientUrl, null, AcknowledgementType.class);

		return null;
	}

	@Override
	public AcknowledgementTypeDto archiveTransfer(ArchiveTransferTypeDto archiveTransferDto) {

		return restTemplate.postForObject(avpServerUrl, archiveTransferDto, AcknowledgementTypeDto.class);

	}

	@Override
	public AcknowledgementType replyArchiveTransfer(ArchiveTransferReplyType archiveTransferReplyType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementTypeDto archiveDelivery(ArchiveDeliveryRequestTypeDto archiveDeliveryRequestTypeDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementType replyArchiveDelivery(ArchiveDeliveryRequestReplyType archiveDeliveryRequestReplyType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementType notifyArchiveModification(
			ArchiveModificationNotificationType archiveModificationNotificationType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementType notifyArvhiveDestruction(
			ArchiveDestructionNotificationType archiveDestructionNotificationType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementType requireArchiveRestitution(ArchiveRestitutionRequestType archiveRestitutionRequestType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementTypeDto replyArchiveRestitutionRequest(
			ArchiveRestitutionRequestReplyTypeDto archiveRestitutionRequestReplyTypeDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementTypeDto requireArchiveRestitution(
			ArchiveRestitutionRequestTypeDto archiveRestitutionRequestTypeDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementType replyArchiveRestitutionRequest(
			ArchiveRestitutionRequestReplyType archiveRestitutionRequestReplyType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementType archiveRestitution(ArchiveRestitutionType archiveRestitutionType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementType requireAuthorizationOriginatingAgency(
			AuthorizationOriginatingAgencyRequestType authorizationOriginatingAgencyRequestType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementTypeDto replyAuthorizationOriginatingAgencyRequest(
			AuthorizationOriginatingAgencyRequestReplyTypeDto authorizationOriginatingAgencyRequestReplyTypeDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementType requireAuthorizationControlAuthority(
			AuthorizationControlAuthorityRequestType authorizationControlAuthorityRequestType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcknowledgementTypeDto replyAuthorizationControlAuthorityRequest(
			AuthorizationControlAuthorityRequestReplyTypeDto authorizationControlAuthorityRequestReplyTypeDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
