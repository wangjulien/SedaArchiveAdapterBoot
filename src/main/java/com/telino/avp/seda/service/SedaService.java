package com.telino.avp.seda.service;

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

/**
 * @author jwang
 *
 */
public interface SedaService {

	/**
	 * Demande de transfere des archives par Service Versant
	 * 
	 * @param archiveTransferRequestTypeDto
	 * @return
	 */
	public AcknowledgementTypeDto requireArchiveTransfer(ArchiveTransferRequestTypeDto archiveTransferRequestTypeDto);

	public AcknowledgementType replyArchiveTransferRequest(ArchiveTransferRequestReplyType archiveTransferRequestReplyType);

	/**
	 * Transfere des archives par Service Versant
	 * 
	 * @param archiveTransferTypeDto
	 * @return
	 */
	public AcknowledgementTypeDto archiveTransfer(ArchiveTransferTypeDto archiveTransferTypeDto);

	public AcknowledgementType replyArchiveTransfer(ArchiveTransferReplyType archiveTransferReplyType);

	/**
	 * Demander de communication des archives par Demandeur d'archives
	 * 
	 * @param archiveDeliveryRequestTypeDto
	 * @return
	 */
	public AcknowledgementTypeDto archiveDelivery(ArchiveDeliveryRequestTypeDto archiveDeliveryRequestTypeDto);

	public AcknowledgementType replyArchiveDelivery(ArchiveDeliveryRequestReplyType archiveDeliveryRequestReplyType);

	/**
	 * Signaler la modification des archives par Service d'Archives
	 * 
	 * @param archiveModificationNotificationType
	 */
	public AcknowledgementType notifyArchiveModification(ArchiveModificationNotificationType archiveModificationNotificationType);

	/**
	 * Signaler la destruciton des archives par Service d'Archives
	 * 
	 * @param archiveDestructionNotificationType
	 */
	public AcknowledgementType notifyArvhiveDestruction(ArchiveDestructionNotificationType archiveDestructionNotificationType);

	
	/**
	 * Demande de restitution des archives par Service d'Archives
	 * 
	 * @param archiveRestitutionRequestType
	 */
	public AcknowledgementType requireArchiveRestitution(ArchiveRestitutionRequestType archiveRestitutionRequestType);

	public AcknowledgementTypeDto replyArchiveRestitutionRequest(
			ArchiveRestitutionRequestReplyTypeDto archiveRestitutionRequestReplyTypeDto);

	/**
	 * Demande de restitution des archives par Service Producteur
	 * 
	 * @param archiveRestitutionRequestTypeDto
	 * @return
	 */
	public AcknowledgementTypeDto requireArchiveRestitution(
			ArchiveRestitutionRequestTypeDto archiveRestitutionRequestTypeDto);

	public AcknowledgementType replyArchiveRestitutionRequest(ArchiveRestitutionRequestReplyType archiveRestitutionRequestReplyType);

	/**
	 * Restitution realle des archive par Service d'Archives
	 * 
	 * @param archiveRestitutionType
	 */
	public AcknowledgementType archiveRestitution(ArchiveRestitutionType archiveRestitutionType);

	/**
	 * Demande d'autorisation au service Producteur
	 * 
	 * @param authorizationOriginatingAgencyRequestType
	 */
	public AcknowledgementType requireAuthorizationOriginatingAgency(
			AuthorizationOriginatingAgencyRequestType authorizationOriginatingAgencyRequestType);

	public AcknowledgementTypeDto replyAuthorizationOriginatingAgencyRequest(
			AuthorizationOriginatingAgencyRequestReplyTypeDto authorizationOriginatingAgencyRequestReplyTypeDto);

	/**
	 * Demande d'autorisation au service de Controle
	 * 
	 * @param authorizationControlAuthorityRequestType
	 */
	public AcknowledgementType requireAuthorizationControlAuthority(
			AuthorizationControlAuthorityRequestType authorizationControlAuthorityRequestType);

	public AcknowledgementTypeDto replyAuthorizationControlAuthorityRequest(
			AuthorizationControlAuthorityRequestReplyTypeDto authorizationControlAuthorityRequestReplyTypeDto);

}
