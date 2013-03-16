package org.openmrs.module.cpm.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptResponsePackageDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReviewController {

	//
	// Pages
	//

	@RequestMapping(value = "module/cpm/proposalReview.list", method = RequestMethod.GET)
	public String listProposalReview() {
		return "/module/cpm/proposalReview";
	}

	//
	// REST endpoints
	//

	@RequestMapping(value = "/cpm/proposalResponses", method = RequestMethod.GET)
	public @ResponseBody
	ArrayList<ProposedConceptResponsePackageDto> getProposalResponses() {

		final List<ProposedConceptResponsePackage> allConceptProposalResponsePackages = Context.getService(ProposedConceptService.class).getAllProposedConceptResponsePackages();
		final ArrayList<ProposedConceptResponsePackageDto> response = new ArrayList<ProposedConceptResponsePackageDto>();

		for (final ProposedConceptResponsePackage conceptProposalResponsePackage : allConceptProposalResponsePackages) {

			final ProposedConceptResponsePackageDto conceptProposalResponsePackageDto = createProposedConceptResponsePackageDto(conceptProposalResponsePackage);
			response.add(conceptProposalResponsePackageDto);
		}

		return response;
	}

	private ProposedConceptResponsePackageDto createProposedConceptResponsePackageDto(final ProposedConceptResponsePackage conceptProposalResponsePackage) {

		final ProposedConceptResponsePackageDto conceptProposalPackageDto = new ProposedConceptResponsePackageDto();
		conceptProposalPackageDto.setId(conceptProposalResponsePackage.getId());
		conceptProposalPackageDto.setName(conceptProposalResponsePackage.getName());
		conceptProposalPackageDto.setEmail(conceptProposalResponsePackage.getEmail());
		conceptProposalPackageDto.setDescription(conceptProposalResponsePackage.getDescription());

		final Set<ProposedConceptResponse> proposedConcepts = conceptProposalResponsePackage.getProposedConcepts();
		final List<ProposedConceptDto> list = new ArrayList<ProposedConceptDto>();

		for (final ProposedConceptResponse conceptProposal : proposedConcepts) {

			final ProposedConceptDto conceptProposalDto = new ProposedConceptDto();
//			conceptProposalDto.setConceptId(conceptProposal.get??)
			conceptProposalDto.setName(conceptProposal.getName());
//			conceptProposalDto.setComments(conceptProposal.getComments()); type mismatch
			conceptProposalDto.setStatus(conceptProposal.getStatus());

			list.add(conceptProposalDto);
		}

		conceptProposalPackageDto.setConcepts(list);
		return conceptProposalPackageDto;
	}
}
