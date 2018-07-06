package com.telino.avp.seda.rest;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telino.avp.protocol.AvpProtocol;
import com.telino.avp.seda.dto.ArchiveDeliveryRequestTypeDto;
import com.telino.avp.seda.dto.ArchiveRestitutionRequestReplyTypeDto;
import com.telino.avp.seda.dto.ArchiveRestitutionRequestTypeDto;
import com.telino.avp.seda.dto.ArchiveTransferRequestTypeDto;
import com.telino.avp.seda.dto.ArchiveTransferTypeDto;
import com.telino.avp.seda.dto.AuthorizationControlAuthorityRequestReplyTypeDto;
import com.telino.avp.seda.dto.AuthorizationOriginatingAgencyRequestReplyTypeDto;
import com.telino.avp.seda.service.SedaService;
import com.telino.avp.seda.v1.types.AcknowledgementType;
import com.telino.avp.seda.v1.types.ArchiveDeliveryRequestType;
import com.telino.avp.seda.v1.types.ArchiveRestitutionRequestReplyType;
import com.telino.avp.seda.v1.types.ArchiveRestitutionRequestType;
import com.telino.avp.seda.v1.types.ArchiveTransferRequestType;
import com.telino.avp.seda.v1.types.ArchiveTransferType;
import com.telino.avp.seda.v1.types.AuthorizationControlAuthorityRequestReplyType;
import com.telino.avp.seda.v1.types.AuthorizationOriginatingAgencyRequestReplyType;
import com.telino.avp.seda.v1.types.ObjectFactory;

/**
 * 
 * REST API qui suit le norm SEDA (V1)
 * 
 * @author jwang
 *
 */
@RestController
@RequestMapping(value = AvpProtocol.SEDA_RES, consumes = MediaType.APPLICATION_ATOM_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
public class SedaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SedaController.class);

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SedaService sedaService;

	@Autowired
	private ObjectFactory sedaObjectFactory;

	/**
	 * Demande de transfere des archives par Service Versant
	 * 
	 * @param archiveTransferRequest
	 *            : XML Seda ArchiveTransferRequestType
	 * @return
	 */
	@PostMapping(AvpProtocol.TRANSFER_REQUEST)
	public ResponseEntity<AcknowledgementType> requireArchiveTransfer(
			@Valid @RequestBody final ArchiveTransferRequestType archiveTransferRequest) {
		LOGGER.info("ArchiveTransferRequest received : {} ", archiveTransferRequest.getTransferRequestIdentifier());

		sedaService
				.requireArchiveTransfer(modelMapper.map(archiveTransferRequest, ArchiveTransferRequestTypeDto.class));

		return ResponseEntity.ok(sedaObjectFactory.createAcknowledgementType());
	}

	/**
	 * Transfere des archives par Service Versant
	 * 
	 * @param archiveTransfer
	 * @return
	 */
	@PostMapping(AvpProtocol.TRANSFER)
	public ResponseEntity<AcknowledgementType> archiveTransfer(
			@Valid @RequestBody final ArchiveTransferType archiveTransfer) {
		LOGGER.info("ArchiveTransfer received : {} ", archiveTransfer.getTransferIdentifier());

		sedaService.archiveTransfer(modelMapper.map(archiveTransfer, ArchiveTransferTypeDto.class));

		return ResponseEntity.ok(sedaObjectFactory.createAcknowledgementType());
	}

	/**
	 * Demande de communication de l'archive
	 * 
	 * @param archiveDeliveryRequest
	 * @return
	 */
	@PostMapping(AvpProtocol.DELIVERY_REQUEST)
	public ResponseEntity<AcknowledgementType> requireArchiveDelivery(
			@Valid @RequestBody final ArchiveDeliveryRequestType archiveDeliveryRequest) {
		LOGGER.info("ArchiveDeliveryRequest received : {} ", archiveDeliveryRequest.getDeliveryRequestIdentifier());

		sedaService.archiveDelivery(modelMapper.map(archiveDeliveryRequest, ArchiveDeliveryRequestTypeDto.class));

		return ResponseEntity.ok(sedaObjectFactory.createAcknowledgementType());
	}

	/**
	 * Demander de communication des archives par Demandeur d'archives
	 * 
	 * @param archiveRestitutionRequestTypeDto
	 * @return
	 */
	@PostMapping(AvpProtocol.RESTITUTION_REQUEST)
	public ResponseEntity<AcknowledgementType> requireArchiveRestitution(
			@Valid @RequestBody final ArchiveRestitutionRequestType archiveRestitutionRequest) {

		LOGGER.info("ArchiveRestitutionRequest received : {} ",
				archiveRestitutionRequest.getRestitutionRequestIdentifier());

		sedaService.requireArchiveRestitution(
				modelMapper.map(archiveRestitutionRequest, ArchiveRestitutionRequestTypeDto.class));

		return ResponseEntity.ok(sedaObjectFactory.createAcknowledgementType());
	}

	/**
	 * Response de Producer de la demande de de communication des archives par
	 * Service d'Archives
	 * 
	 * @param archiveRestitutionRequestReply
	 * @return
	 */
	@PostMapping(AvpProtocol.RESTITUTION_REQUEST_REPLY)
	public ResponseEntity<AcknowledgementType> replyArchiveRestitutionRequest(
			@Valid @RequestBody final ArchiveRestitutionRequestReplyType archiveRestitutionRequestReply) {

		LOGGER.info("ArchiveRestitutionRequestReply received : {} ",
				archiveRestitutionRequestReply.getRestitutionRequestReplyIdentifier());

		sedaService.replyArchiveRestitutionRequest(
				modelMapper.map(archiveRestitutionRequestReply, ArchiveRestitutionRequestReplyTypeDto.class));

		return ResponseEntity.ok(sedaObjectFactory.createAcknowledgementType());
	}

	/**
	 * Response de la demande d'autorisation au service Producteur
	 * 
	 * @param authorizationOriginatingAgencyRequestReply
	 * @return
	 */
	@PostMapping(AvpProtocol.AUTHORIZATION_ORIGINATING_AGENCY_REQUEST_REPLY)
	public ResponseEntity<AcknowledgementType> replyAuthorizationOriginatingAgencyRequest(
			@Valid @RequestBody final AuthorizationOriginatingAgencyRequestReplyType authorizationOriginatingAgencyRequestReply) {

		LOGGER.info("AuthorizationOriginatingAgencyRequestReply received : {} ",
				authorizationOriginatingAgencyRequestReply.getAuthorizationOriginatingAgencyRequestReplyIdentifier());

		sedaService.replyAuthorizationOriginatingAgencyRequest(modelMapper.map(
				authorizationOriginatingAgencyRequestReply, AuthorizationOriginatingAgencyRequestReplyTypeDto.class));

		return ResponseEntity.ok(sedaObjectFactory.createAcknowledgementType());
	}

	/**
	 * Response de la demande d'autorisation au service de Controle
	 * 
	 * @param authorizationControlAuthorityRequestReply
	 * @return
	 */
	@PostMapping(AvpProtocol.AUTHORIZATION_CONTROL_AUTHORITY_REQUEST_REPLY)
	public ResponseEntity<AcknowledgementType> replyAuthorizationControlAuthorityRequest(
			@Valid @RequestBody final AuthorizationControlAuthorityRequestReplyType authorizationControlAuthorityRequestReply) {

		LOGGER.info("AuthorizationControlAuthorityRequestReply received : {} ",
				authorizationControlAuthorityRequestReply.getAuthorizationControlAuthorityRequestReplyIdentifier());

		sedaService.replyAuthorizationControlAuthorityRequest(modelMapper.map(authorizationControlAuthorityRequestReply,
				AuthorizationControlAuthorityRequestReplyTypeDto.class));

		return ResponseEntity.ok(sedaObjectFactory.createAcknowledgementType());
	}
}
