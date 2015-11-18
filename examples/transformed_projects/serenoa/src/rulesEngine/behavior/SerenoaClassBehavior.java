package rulesEngine.behavior;

import java.util.BitSet;
import java.util.List;
import java.util.Vector;

import leon.app.LySession;
import leon.app.behavior.LyClassBehavior;
import leon.control.LyConsultController;
import leon.control.LyController;
import leon.control.LyFilterController;
import leon.control.LyFormController;
import leon.control.LyOutlineController;
import leon.control.LySetController;
import leon.data.LyContext;
import leon.data.LyObject;
import leon.data.LyObjectList;
import leon.info.LyAction;
import leon.info.LyArrayFieldInfo;
import leon.info.LyChoiceFieldInfo;
import leon.info.LyChoiceOption;
import leon.info.LyChoiceOptionGroup;
import leon.info.LyClassInfo;
import leon.info.LyFieldInfo;
import leon.info.LyFilter;
import leon.info.LyFilterElement;
import leon.info.LyFilterExpression.Condition;
import leon.info.LyInfo;
import leon.info.LyInfoDataList;
import leon.info.LyInfoList;
import leon.info.LyRelationFieldInfo;
import leon.info.LyTextFieldInfo;
import rulesEngine.manager.SERENOA_CONSTANTES;

public class SerenoaClassBehavior extends LyClassBehavior {

	public SerenoaClassBehavior() {		
		System.out.println("serenoa class behavior active");	
	}

	@Override
	public boolean isDisplayed(LyFormController form, LyFieldInfo fieldInfo) {
		boolean mobileMode = new Boolean(form.getEnvironment().getEnv(
				SERENOA_CONSTANTES.SERENOA_MODE_PREFIX + "mobile"));
		if (mobileMode && fieldInfo.hasMark(LyFieldInfo.MARK_OPTIONAL)) {
			//return false;
		}
		List<String> hiddenFields = (List<String>) form.getSession().getValue(SERENOA_CONSTANTES.SERENOA_HIDDEN_FIELDS);
		if(hiddenFields.contains(fieldInfo.getId())){
			return false;
		}
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo.getId());
		return super.isDisplayed(form, fieldInfoSerenoa);
	}

	@Override
	public boolean isOptional(LySetController set, LyFieldInfo fieldInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo.getId());
		return super.isOptional(set, fieldInfoSerenoa);		
	}

	@Override
	public void changeState(LyController parent, LyAction action,
			LyObjectList objects, LyFieldInfo fieldInfo) {

		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo.getId());
		super.changeState(parent, action, objects, fieldInfoSerenoa);
	}

//	@Override
//	public Object getDefaultValue(LySetController set, LyFieldInfo fieldInfo) {
//		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo.getId());
//		return super.getDefaultValue(set, fieldInfoSerenoa);
//	}

	@Override
	public LyFilterElement buildFilterElement(LyFilterController filter,
			LyFieldInfo fieldInfo, boolean not, Condition condition,
			Object value, BitSet modifiers) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo.getId());
		return super.buildFilterElement(filter, fieldInfoSerenoa, not,
				condition, value, modifiers);
	}

	@Override
	public boolean enableChoiceOption(LySetController set,
			LyChoiceFieldInfo choice, LyChoiceOption option) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, choice);
		return super.enableChoiceOption(set,
				(LyChoiceFieldInfo) fieldInfoSerenoa, option);
	}

	@Override
	public boolean enableRelationHRef(LyConsultController consult,
			LyRelationFieldInfo relation, LyObject href) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);
		return super.enableRelationHRef(consult,
				(LyRelationFieldInfo) fieldInfoSerenoa, href);
	}

	@Override
	public boolean enableRelationLink(LySetController set,
			LyRelationFieldInfo relation, LyObject link) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.enableRelationLink(set, (LyRelationFieldInfo) fieldInfoSerenoa, link);
	}

	@Override
	public boolean enableRelationLink(LySetController set,
			LyRelationFieldInfo relation, LyObject link, boolean showError) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);
		return super.enableRelationLink(set, (LyRelationFieldInfo) fieldInfoSerenoa, link, showError);
	}

	@Override
	public LyAction getAction(LyConsultController consult,
			LyRelationFieldInfo relation, LyClassInfo classInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getAction(consult, (LyRelationFieldInfo) fieldInfoSerenoa, classInfo);
	}

	@Override
	public int getAutoCompletionMaximum(LySetController set,
			LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getAutoCompletionMaximum(set, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public boolean isEditable(LySetController set, LyFieldInfo fieldInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		return super.isEditable(set, fieldInfoSerenoa);
	}

	@Override
	public boolean isEnabled(LySetController set, LyFieldInfo fieldInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		return super.isEnabled(set, fieldInfoSerenoa);
	}

	@Override
	public int getAutoCompletionMaximum(LySetController set,
			LyTextFieldInfo fieldInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		return super.getAutoCompletionMaximum(set, (LyTextFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public String[] getAutoCompletionValues(LySetController set,
			LyTextFieldInfo fieldInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		return super.getAutoCompletionValues(set, (LyTextFieldInfo) fieldInfoSerenoa);
	}

	@Override
	protected LyInfoList<LyInfo> getChoiceContextOptions(LySetController set,
			LyChoiceFieldInfo choice) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, choice);		
		return super.getChoiceContextOptions(set, (LyChoiceFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public boolean isValidGroup(LySetController set, LyChoiceFieldInfo choice,
			LyChoiceOption option, LyChoiceOptionGroup group) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, choice);		
		return super.isValidGroup(set, (LyChoiceFieldInfo) fieldInfoSerenoa, option, group);
	}

	@Override
	public boolean validateChoiceOption(LySetController set,
			LyChoiceFieldInfo choice, LyChoiceOption option) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, choice);		
		return super.validateChoiceOption(set, (LyChoiceFieldInfo) fieldInfoSerenoa, option);
	}

	@Override
	public LyObjectList getRelationDomain(LySetController set,
			LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getRelationDomain(set, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public LyObjectList getFilterRelationDomain(LyFilterController filter,
			LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getFilterRelationDomain(filter, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public LyInfoList<LyClassInfo> getFilterRelationClasses(
			LyFormController filter, LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getFilterRelationClasses(filter, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public Vector<LyChoiceOption> getFilterChoiceDomain(
			LyFilterController filter, LyChoiceFieldInfo choice) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, choice);		
		return super.getFilterChoiceDomain(filter, (LyChoiceFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public LyFilter getRelationFilter(LySetController controller,
			LyRelationFieldInfo relation, LyClassInfo classInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getRelationFilter(controller, (LyRelationFieldInfo) fieldInfoSerenoa, classInfo);
	}

	@Override
	public LyInfoList<LyClassInfo> getRelationClasses(LySetController set,
			LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getRelationClasses(set, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public LyObjectList getRelationContext(LySetController set,
			LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getRelationContext(set, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public boolean useLocalCompletion(LySetController set,
			LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.useLocalCompletion(set, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public LyObjectList getRelationDomain(LySetController set,
			LyRelationFieldInfo relation, String prefix, boolean fill) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getRelationDomain(set, (LyRelationFieldInfo) fieldInfoSerenoa, prefix, fill);
	}

	@Override
	public String getLinkShortLabel(LySetController set,
			LyRelationFieldInfo relation, LyObject link) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getLinkShortLabel(set, (LyRelationFieldInfo) fieldInfoSerenoa, link);
	}

	@Override
	public LyInfoDataList<LyAction, LyClassInfo> getTransverseActions(
			LySetController set, LyFieldInfo fieldInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		return super.getTransverseActions(set, fieldInfoSerenoa);
	}

	@Override
	public LyInfoDataList<LyAction, LyClassInfo> getTransverseActions(
			LySetController set, LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getTransverseActions(set, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public LyInfoDataList<LyAction, LyClassInfo> getFilterTransverseActions(
			LyFilterController filter, LyFieldInfo fieldInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		return super.getFilterTransverseActions(filter, fieldInfoSerenoa);
	}

	@Override
	public LyInfoDataList<LyAction, LyClassInfo> getFilterTransverseActions(
			LyFilterController filter, LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getFilterTransverseActions(filter, fieldInfoSerenoa);
	}

	@Override
	public boolean needCheck(LySetController set, LyFieldInfo fieldInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		return super.needCheck(set, fieldInfoSerenoa);
	}

	@Deprecated
	@Override
	protected void propagate(LySetController set, LyFieldInfo fieldInfo,
			Object newValue) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		super.propagate(set, fieldInfoSerenoa, newValue);
	}

		
	@Deprecated
	@Override
	protected short checkNewValue(LySetController set, LyFieldInfo fieldInfo,
			Object newValue) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		return super.checkNewValue(set, fieldInfoSerenoa, newValue);
	}
	

	@Override
	protected Object getNextValue(LySession session, LyClassInfo classInfo,
			LyFieldInfo fieldInfo, Object oldValue) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		return super.getNextValue(session, classInfo, fieldInfoSerenoa, oldValue);
	}

	@Override
	public boolean useEditTable(LySetController controller,
			LyArrayFieldInfo array) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, array);		
		return super.useEditTable(controller, (LyArrayFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public boolean useEditTable(LySetController controller,
			LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.useEditTable(controller, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public LyAction getSetAction(LyOutlineController outline,
			LyFieldInfo fieldInfo) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, fieldInfo);		
		return super.getSetAction(outline, fieldInfoSerenoa);
	}

	@Override
	public LyContext.Position getInsertPosition(LySetController controller,
			LyRelationFieldInfo relation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, relation);		
		return super.getInsertPosition(controller, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

	@Override
	public boolean useSingleContext(LySetController controller,
			LyRelationFieldInfo parentRelation) {
		LyFieldInfo fieldInfoSerenoa = SerenoaSessionBehavior.getSerenoaFieldInfo(_session, parentRelation);		
		return super.useSingleContext(controller, (LyRelationFieldInfo) fieldInfoSerenoa);
	}

}
