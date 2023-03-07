# Eiffel CDEvents mapping

This is the set of Eiffel events that will be translated to corresponding [CDEvents](https://cdevents.dev/docs/) and the other way 

| Eiffel Events    | CDEvents       | Comments |
| ---------   | ------------- | ------- |
| EiffelActivityTriggeredEvent (ActT) | dev.cdevents.pipelinerun.queued.0.1.0, dev.cdevents.build.queued.0.1.0| Applicable for PipelineRun and Build CDEvents |
| EiffelActivityStartedEvent (ActS) | dev.cdevents.pipelinerun.started.0.1.0, dev.cdevents.taskrun.started.0.1.0, dev.cdevents.build.started.0.1.0| Applicable for Pipeline, Build and Taskrun CDEvents |
| EiffelArtifactCreatedEvent (ArtC) | dev.cdevents.artifact.packaged.0.1.0 |  |
| EiffelArtifactPublishedEvent (ArtP) | dev.cdevents.artifact.published.0.1.0 |  |
| EiffelEnvironmentDefinedEvent (ED) | dev.cdevents.environment.created.0.1.0 |  |
| EiffelSourceChangeCreatedEvent (SCC) | dev.cdevents.change.created.0.1.0 |  |
| EiffelSourceChangeSubmittedEvent (SCS) | dev.cdevents.change.merged.0.1.0 |  |
| EiffelTestCaseTriggeredEvent (TCT) | dev.cdevents.testcase.queued.0.1.0 |  |
| EiffelTestCaseStartedEvent (TCS) | dev.cdevents.testcase.started.0.1.0 |  |
| EiffelTestCaseFinishedEvent (TCF) | dev.cdevents.testcase.finished.0.1.0 |  |
| EiffelTestSuiteStartedEvent (TSS) | dev.cdevents.testsuite.started.0.1.0 |  |
| EiffelTestSuiteFinishedEvent (TSF) | dev.cdevents.testsuite.finished.0.1.0 |  |

Below are the Eiffel events for those there is no mapping to CDEvent

| Eiffel Events    | CDEvents       |
| ---------   | ------------- |
| EiffelTestCaseCanceledEvent (TCC) | NA |
| EiffelFlowContextDefinedEvent (FCD) | NA |
| EiffelCompositionDefinedEvent (CD) | NA |
| EiffelConfidenceLevelModifiedEvent (CLM) | NA |
| EiffelArtifactReusedEvent (ArtR) | NA |
| EiffelActivityCanceledEvent (ActC) | NA |
| EiffelIssueVerifiedEvent (IV) | NA |
| EiffelTestExecutionRecipeCollectionCreatedEvent (TERCC) | NA |
| EiffelAnnouncementPublishedEvent (AnnP) | NA |
| EiffelIssueDefinedEvent (ID) | NA |


Below are the CDEvents for those there is no mapping to Eiffel event

| CDEvents    | Eiffel Events       |
| ---------   | ------------- |
| dev.cdevents.repository.created.0.1.0 | NA |
| dev.cdevents.repository.modified.0.1.0 | NA |
| dev.cdevents.repository.deleted.0.1.0 | NA |
| dev.cdevents.branch.created.0.1.0 | NA |
| dev.cdevents.branch.deleted.0.1.0 | NA |
| dev.cdevents.change.reviewed.0.1.0 | NA |
| dev.cdevents.change.abandoned.0.1.0 | NA |
| dev.cdevents.change.updated.0.1.0 | NA |
| dev.cdevents.environment.modified.0.1.0 | NA |
| dev.cdevents.environment.deleted.0.1.0 | NA |
| dev.cdevents.service.deployed.0.1.0 | NA |
| dev.cdevents.service.upgraded.0.1.0 | NA |
| dev.cdevents.service.rolledback.0.1.0 | NA |
| dev.cdevents.service.removed.0.1.0 | NA |
| dev.cdevents.service.published.0.1.0 | NA |

