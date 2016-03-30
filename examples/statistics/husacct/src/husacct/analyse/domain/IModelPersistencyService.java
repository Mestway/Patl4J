package husacct.analyse.domain;

import org.dom4j.Element;

public interface IModelPersistencyService {

    public Element saveModel();

    public void loadModel(Element analyseElement);
}
