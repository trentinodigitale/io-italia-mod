export class Servizio {

    constructor(){
    }

    public idObj: number;
    public nomeEnte: string;
    public nomeDipartimento: string;
    public nomeServizio: string;
    public codiceIdentificativo: string;
    public codiceServizioIoItalia: string;
    public codiceFiscale: string;
    public email: string;
    public emailPec: string;
    public tokenIoItalia: string;

    public version: number;
    public erroreImprevisto: string;
}

export class ServizioPaginato {
    public totalItems: number;
    public servizioDTOs: Servizio[];
    public totalPages: number;
    public sortBy: string;
    public currentPage: string;
}