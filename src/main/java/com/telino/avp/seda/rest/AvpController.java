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
import com.telino.avp.seda.dto.AcknowledgementTypeDto;
import com.telino.avp.seda.dto.ArchiveDeliveryRequestReplyTypeDto;
import com.telino.avp.seda.dto.ArchiveDestructionNotificationTypeDto;
import com.telino.avp.seda.dto.ArchiveModificationNotificationTypeDto;
import com.telino.avp.seda.dto.ArchiveRestitutionRequestReplyTypeDto;
import com.telino.avp.seda.dto.ArchiveRestitutionRequestTypeDto;
import com.telino.avp.seda.dto.ArchiveRestitutionTypeDto;
import com.telino.avp.seda.dto.ArchiveTransferReplyTypeDto;
import com.telino.avp.seda.dto.ArchiveTransferRequestReplyTypeDto;
import com.telino.avp.seda.dto.AuthorizationControlAuthorityRequestTypeDto;
import com.telino.avp.seda.dto.AuthorizationOriginatingAgencyRequestTypeDto;
import com.telino.avp.seda.service.SedaService;
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

/**
 * 
 * REST API qui suit le norm SEDA (V1)
 * 
 * @author jwang
 *
 */
@RestController
@RequestMapping(value = AvpProtocol.AVP_RES, consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
public class AvpController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AvpController.class);

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SedaService sedaService;

	
	/**
	 * Response de la demande de transfere des archives par Service Versant
	 * 
	 * @param archiveTransferRequest
	 *            : XML Seda ArchiveTransferRequestType
	 * @return
	 */
	@PostMapping(AvpProtocol.TRANSFER_REQUEST_REPLY)
	public ResponseEntity<AcknowledgementTypeDto> replyArchiveTransferRequest(
			@Valid @RequestBody final ArchiveTransferRequestReplyTypeDto archiveTransferRequestReplyDto) {
		LOGGER.info("ArchiveTransferRequestReply received : {} ", archiveTransferRequestReplyDto);

		sedaService.replyArchiveTransferRequest(
				modelMapper.map(archiveTransferRequestReplyDto, ArchiveTransferRequestReplyType.class));

		return ResponseEntity.ok(new AcknowledgementTypeDto());
	}

	/**
	 * Response de transfere des archives par Service Versant
	 * 
	 * @param archiveTransfer
	 * @return
	 */
	@PostMapping(AvpProtocol.TRANSFER_REPLY)
	public ResponseEntity<AcknowledgementTypeDto> replyArchiveTransfer(
			@Valid @RequestBody final ArchiveTransferReplyTypeDto archiveTransferReplyDto) {
		LOGGER.info("ArchiveTransfer received : {} ", archiveTransferReplyDto);

		sedaService.replyArchiveTransfer(modelMapper.map(archiveTransferReplyDto, ArchiveTransferReplyType.class));

		return ResponseEntity.ok(new AcknowledgementTypeDto());
	}

	/**
	 * Response de la demande de communication des archives par Demandeur d'archives
	 * 
	 * @param archiveDeliveryRequestReplyDto
	 * @return
	 */
	@PostMapping(AvpProtocol.DELIVERY_REQUEST_REPLY)
	public ResponseEntity<AcknowledgementTypeDto> replyArchiveDeliveryRequest(
			@Valid @RequestBody final ArchiveDeliveryRequestReplyTypeDto archiveDeliveryRequestReplyDto) {
		LOGGER.info("ArchiveDeliveryRequestReply received : {} ", archiveDeliveryRequestReplyDto);

		sedaService.replyArchiveDelivery(
				modelMapper.map(archiveDeliveryRequestReplyDto, ArchiveDeliveryRequestReplyType.class));

		return ResponseEntity.ok(new AcknowledgementTypeDto());
	}

	/**
	 * Signaler la modification des archives par Service d'Archives
	 * 
	 * @param archiveModificationNotificationDto
	 * @return
	 */
	@PostMapping(AvpProtocol.MODIFICATION_NOTIFICATION)
	public ResponseEntity<AcknowledgementTypeDto> notifyArchiveModification(
			@Valid @RequestBody final ArchiveModificationNotificationTypeDto archiveModificationNotificationDto) {

		LOGGER.info("ArchiveModificationNotification received : {} ", archiveModificationNotificationDto);

		sedaService.notifyArchiveModification(
				modelMapper.map(archiveModificationNotificationDto, ArchiveModificationNotificationType.class));

		return ResponseEntity.ok(new AcknowledgementTypeDto());
	}

	/**
	 * Signaler la destruciton des archives par Service d'Archives
	 * 
	 * @param archiveDestructionNotificationDto
	 * @return
	 */
	@PostMapping(AvpProtocol.DESTRUCTION_NOTIFICATION)
	public ResponseEntity<AcknowledgementTypeDto> notifyArvhiveDestruction(
			@Valid @RequestBody final ArchiveDestructionNotificationTypeDto archiveDestructionNotificationDto) {

		LOGGER.info("ArchiveDestructionNotification received : {} ", archiveDestructionNotificationDto);

		sedaService.notifyArvhiveDestruction(
				modelMapper.map(archiveDestructionNotificationDto, ArchiveDestructionNotificationType.class));
		
		return ResponseEntity.ok(new AcknowledgementTypeDto());
	}
	
	
	/**
	 * Demande de restitution des archives par Service d'Archives
	 * 
	 * @param archiveRestitutionRequestDto
	 * @return
	 */
	@PostMapping(AvpProtocol.RESTITUTION_REQUEST)
	public ResponseEntity<AcknowledgementTypeDto> requireArchiveRestitution(
			@Valid @RequestBody final ArchiveRestitutionRequestTypeDto archiveRestitutionRequestDto) {

		LOGGER.info("ArchiveRestitutionRequest received : {} ", archiveRestitutionRequestDto);

		sedaService.requireArchiveRestitution(
				modelMapper.map(archiveRestitutionRequestDto, ArchiveRestitutionRequestType.class));
		
		return ResponseEntity.ok(new AcknowledgementTypeDto());
	}
	
	
	/**
	 * Response de Producer de la demande de de communication des archives par Demandeur d'archives
	 * 
	 * @param archiveRestitutionRequestReplyDto
	 * @return
	 */
	@PostMapping(AvpProtocol.RESTITUTION_REQUEST_REPLY)
	public ResponseEntity<AcknowledgementTypeDto> replyArchiveRestitutionRequest(
			@Valid @RequestBody final ArchiveRestitutionRequestReplyTypeDto archiveRestitutionRequestReplyDto) {

		LOGGER.info("ArchiveRestitutionRequestReply received : {} ", archiveRestitutionRequestReplyDto);

		sedaService.replyArchiveRestitutionRequest(
				modelMapper.map(archiveRestitutionRequestReplyDto, ArchiveRestitutionRequestReplyType.class));

		return ResponseEntity.ok(new AcknowledgementTypeDto());
	}
	
	
	/**
	 * Restitution realle des archive par Service d'Archives
	 * 
	 * @param archiveRestitutionDto
	 * @return
	 */
	@PostMapping(AvpProtocol.RESTITUTION)
	public ResponseEntity<AcknowledgementTypeDto> archiveRestitution(
			@Valid @RequestBody final ArchiveRestitutionTypeDto archiveRestitutionDto) {

		LOGGER.info("ArchiveRestitution received : {} ", archiveRestitutionDto);

		sedaService.archiveRestitution(
				modelMapper.map(archiveRestitutionDto, ArchiveRestitutionType.class));

		return ResponseEntity.ok(new AcknowledgementTypeDto());
	}
	
	
	/**
	 * Demande d'autorisation au service Producteur
	 * 
	 * @param authorizationOriginatingAgencyRequestDto
	 * @return
	 */
	@PostMapping(AvpProtocol.AUTHORIZATION_ORIGINATING_AGENCY_REQUEST)
	public ResponseEntity<AcknowledgementTypeDto> requireAuthorizationOriginatingAgency(
			@Valid @RequestBody final AuthorizationOriginatingAgencyRequestTypeDto authorizationOriginatingAgencyRequestDto) {

		LOGGER.info("AuthorizationOriginatingAgencyRequest received : {} ", authorizationOriginatingAgencyRequestDto);

		sedaService.requireAuthorizationOriginatingAgency(
				modelMapper.map(authorizationOriginatingAgencyRequestDto, AuthorizationOriginatingAgencyRequestType.class));

		return ResponseEntity.ok(new AcknowledgementTypeDto());
	}
	
	
	/**
	 * Demande d'autorisation au service de Controle
	 * 
	 * @param authorizationControlAuthorityRequestDto
	 * @return
	 */
	@PostMapping(AvpProtocol.AUTHORIZATION_CONTROL_AUTHORITY_REQUEST)
	public ResponseEntity<AcknowledgementTypeDto> requireAuthorizationControlAuthority(
			@Valid @RequestBody final AuthorizationControlAuthorityRequestTypeDto authorizationControlAuthorityRequestDto) {

		LOGGER.info("AuthorizationControlAuthorityRequest received : {} ", authorizationControlAuthorityRequestDto);

		sedaService.requireAuthorizationControlAuthority(
				modelMapper.map(authorizationControlAuthorityRequestDto, AuthorizationControlAuthorityRequestType.class));

		return ResponseEntity.ok(new AcknowledgementTypeDto());
	}
}
