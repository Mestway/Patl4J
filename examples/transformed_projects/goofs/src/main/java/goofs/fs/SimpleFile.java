package goofs.fs;

public class SimpleFile extends File {

	public SimpleFile(Dir parent, String name) throws Exception {

		super(parent, name, 0777, "");
	}

	@Override
	public int save() {
		return 0;
	}

	@Override
	public int delete() {

		remove();

		return 0;
	}

	@Override
	public int rename(Dir newParent, String name) {

		if (getParent() == newParent) {

			setName(name);
		}

		return 0;
	}

}
