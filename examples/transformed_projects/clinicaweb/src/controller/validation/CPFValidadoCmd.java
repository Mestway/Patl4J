package controller.validation;

public class CPFValidadoCmd implements ValidarCmd {

	public Object validar(String strCpf) throws Exception {
		/**
		 * Evitando NumberFormatException
		 */
		// Long.parseLong(val);
		strCpf = strCpf.replaceAll("-", "");
		strCpf = strCpf.replaceAll("\\.", "");
		strCpf = strCpf.replaceAll(" ", "");
		if (strCpf.length() < 11) {
			throw new Exception();
		} else {
			int d1, d2;
			int digito1, digito2, resto;
			int digitoCPF;
			String nDigResult;

			d1 = d2 = 0;
			digito1 = digito2 = resto = 0;

			for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
				digitoCPF = Integer.valueOf(
						strCpf.substring(nCount - 1, nCount)).intValue();
				// multiplique a ultima casa por 2 a seguinte por 3 a seguinte
				// por 4 e assim por diante.
				d1 = d1 + (11 - nCount) * digitoCPF;
				// para o segundo digito repita o procedimento incluindo o
				// primeiro digito calculado no passo anterior.
				d2 = d2 + (12 - nCount) * digitoCPF;
			}
			;
			// Primeiro resto da divisão por 11.
			resto = (d1 % 11);
			// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é
			// 11 menos o resultado anterior.
			if (resto < 2)
				digito1 = 0;
			else
				digito1 = 11 - resto;
			d2 += 2 * digito1;
			// Segundo resto da divisão por 11.
			resto = (d2 % 11);
			// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é
			// 11 menos o resultado anterior.
			if (resto < 2)
				digito2 = 0;
			else
				digito2 = 11 - resto;
			// Digito verificador do CPF que está sendo validado.
			String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf
					.length());
			// Concatenando o primeiro resto com o segundo.
			nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
			// comparar o digito verificador do cpf com o primeiro resto + o
			// segundo resto.
			if (nDigVerific.equals(nDigResult))
				return strCpf;
			else {
				throw new Exception();
			}
		}
	}
}
