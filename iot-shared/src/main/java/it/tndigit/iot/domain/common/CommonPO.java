   package it.tndigit.iot.domain.common;

   import javax.persistence.*;
   import javax.validation.constraints.NotNull;
   import java.io.Serializable;
   import java.util.Objects;

   @MappedSuperclass
    public abstract class CommonPO implements Serializable {


       private static final long serialVersionUID = -905377684553348881L;

        @Id
        @NotNull
        @SequenceGenerator(name="NXTNBR", sequenceName="NXTNBR", allocationSize=1, initialValue = 1)
        @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NXTNBR")
        protected Long idObj;


        public Long getIdObj() {
            return idObj;
        }

        public void setIdObj(Long idObj) {
            this.idObj = idObj;
        }


       @Override
       public boolean equals(Object o) {
           if (this == o) return true;
           if (!(o instanceof CommonPO)) return false;
           CommonPO commonPO = (CommonPO) o;
           return Objects.equals(idObj, commonPO.idObj);
       }

       @Override
       public int hashCode() {
           return Objects.hash(idObj);
       }
   }
