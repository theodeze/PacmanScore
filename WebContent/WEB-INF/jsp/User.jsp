<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" href="<c:url value="/img/ghost_blue.png"/>"> <title>Accueil - PacmanScore</title>

    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"> <link rel="stylesheet"
        href="<c:url value="/css/Accueil.css"/>"> <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous">
        </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
        integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
        integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
        crossorigin="anonymous"></script>
    <script src="<c:url value="/js/vue.min.js"/>"> </script> <script
        src="https://cdn.jsdelivr.net/npm/vee-validate@latest/dist/vee-validate.js">
        </script>
    <script src="https://cdn.jsdelivr.net/npm/vee-validate@latest/dist/locale/fr.js"></script>

    <!-- Pour les Parties -->

    <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.13.4/bootstrap-table.min.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.13.4/bootstrap-table.min.js"></script>
    <script src="js/tableExport.min.js"></script>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.13.4/extensions/export/bootstrap-table-export.min.js"></script>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.13.4/locale/bootstrap-table-fr-FR.min.js"></script>
    <script src="js/table.js"></script>

    <style>
        body {
            padding-top: 2rem;
            padding-bottom: 2rem;
            background-color: gainsboro;
        }

        .card-body {
            background-color: whitesmoke;
        }
    </style>

</head>

<body>
    <div id="main">
        <c:import url="/jsp/Menu.jsp" />

        <div class="container-fluid justify-content-center">

            <!-- TITRE PACMAN -->

            <div class="bg-info text-white text-center">
                <h1>BIENVENUE SUR PACMAN SCORE</h1>
            </div>

            <div class="pt-4 pb-2 text-center"><img src="<c:url value="/img/Partie.png"/>" alt="Pacman"></div>

            <c:if test="${not empty Success}">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <strong>Succès</strong>
                    <c:out value="${Success}" />
                    <c:remove var="Success" scope="session" />
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>

            <c:if test="${not empty Warning}">
                <div class="alert alert-warning alert-dismissible" role="alert">
                    <strong>Attention</strong>
                    <c:out value="${Warning}" />
                    <ul>
                        <c:if test="${not empty form.erreurs['Identifiant_inscription']}">
                            <li>
                                <c:out value="${form.erreurs['Identifiant_inscription']}" />
                            </li>
                        </c:if>

                        <c:if test="${not empty form.erreurs['Pseudo_inscription']}">
                            <li>
                                <c:out value="${form.erreurs['Pseudo_inscription']}" />
                            </li>
                        </c:if>

                        <c:if test="${not empty form.erreurs['MotDePasse_inscription']}">
                            <li>
                                <c:out value="${form.erreurs['MotDePasse_inscription']}" />
                            </li>
                        </c:if>

                        <c:if test="${not empty form.erreurs['Identifiant_modif_email']}">
                            <li>
                                <c:out value="${form.erreurs['Identifiant_modif_email']}" />
                            </li>
                        </c:if>

                        <c:if test="${not empty form.erreurs['MotDePasse_modif_mdp']}">
                            <li>
                                <c:out value="${form.erreurs['MotDePasse_modif_mdp']}" />
                            </li>
                        </c:if>

                        <c:if test="${not empty form.erreurs['Pseudo_modif_pseudo']}">
                            <li>
                                <c:out value="${form.erreurs['Pseudo_modif_pseudo']}" />
                            </li>
                        </c:if>
                    </ul>

                    <c:remove var="Warning" scope="session" />
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>

            <!-- CONNEXION AU COMPTE / CREATION -->
            <c:if test="${empty sessionScope.sessionUtilisateur}">

                <div class="border border-info mt-2" v-if=v2_account>

                    <div class="row justify-content-center">

                        <div class="col text-right"><button v-on:click="fermer" class="btn btn-info">X</button></div>
                    </div>

                    <div class="row justify-content-center">

                        <div class="col text-center">

                            <h4>Connexion au compte</h4>
                            <form id="divUser" class="form-signin" method="post">
                                <div class="form-label-group">
                                    <input type="text" id=email_connexion name="Identifiant_connexion"
                                        class="form-control" required>
                                    <label for="email_connexion">Email</label>
                                </div>
                                <div class="form-label-group">
                                    <input type="password" id="mdp_connexion" name="MotDePasse_connexion"
                                        class="form-control" required>
                                    <label for="mdp_connexion">Mot de passe</label>
                                </div>
                                <input type="hidden" name="Type" value="Connexion" />
                                <button class="btn btn-info" type="submit">SE CONNECTER</button>
                            </form>
                        </div>
                    </div>
                </div>


                <div class="border border-info mt-2" v-if=v1_account>

                    <div class="row justify-content-center">

                        <div class="col text-right"><button v-on:click="fermer" class="btn btn-info">X</button></div>
                    </div>

                    <div class="row justify-content-center">

                        <div class="col text-center">
                            <h4>Création d'un compte</h4>
                            <form id="User" class="form-signin" method="post">
                                <div class="form-label-group">
                                    <input type="text" id="email_inscription" name="Identifiant_inscription"
                                        class="form-control" required>
                                    <label for="email_inscription">Email</label>

                                </div>
                                <div class="form-label-group">
                                    <input type="text" id="pseudo_inscription" name="Pseudo_inscription"
                                        class="form-control" required>
                                    <label for="pseudo_inscription">Pseudo</label>
                                </div>

                                <div class="form-label-group">
                                    <input v-validate="'required'" id="pwd_inscription" name="MotDePasse_inscription"
                                        type="password" class="form-control" data-vv-as="mot de passe" ref="password"
                                        required>
                                    <label for="pwd_inscription">Mot de Passe</label>
                                </div>

                                <div class="form-label-group">
                                    <input v-validate="'required|confirmed:password'" id="pwd_inscription_conf"
                                        name="MotDePasse_inscription_confirmation" type="password" class="form-control"
                                        data-vv-as="de confirmation" required>
                                    <label for="pwd_inscription_conf">Confirmation</label>
                                </div>

                                <div class="alert alert-danger" v-show="errors.any()">
                                    <div v-if="errors.has('MotDePasse_inscription')">
                                        {{ errors.first('MotDePasse_inscription') }}
                                    </div>
                                    <div v-if="errors.has('MotDePasse_inscription_confirmation')">
                                        {{ errors.first('MotDePasse_inscription_confirmation') }}
                                    </div>
                                </div>
                                <input type="hidden" name="Type" value="Inscription" />

                                <div class="alert alert-warning" v-show="errors.any()">Complétez pour afficher le
                                    bouton de validation</div>
                                <button class="btn btn-info" type="submit" v-show="!errors.any()">CRÉER COMPTE</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="text-center pt-2 pl-2 pr-2 pb-2 mt-4 border border-info">
                    <h5>Sur ce site, vous pouvez visionner vos scores aux différentes parties de Pacman que vous
                        effectuez.
                        vous pouvez aussi comparer votre classement à celui des autres. En espérant qu'il vous plaira ;)
                    </h5>
                </div>
            </c:if>


            <c:if test="${not empty sessionScope.sessionUtilisateur}">
                <!-- Modification du profil -->
                <div class="border border-info mt-2" v-if=v_modif>

                    <div class="row justify-content-center">

                        <div class="col text-right"><button v-on:click="fermer" class="btn btn-info">X</button></div>
                    </div>

                    <div class="row justify-content-center">

                        <div class="col-md-4 text-center">
                            <h4>Modifier Email</h4>

                            <form id="ModifEmail" class="form-signin" method="post">
                                <div class="form-label-group">
                                    <input type="text" id="old_email_modif_email" name="Old_Identifiant_modif_email"
                                        class="form-control" required>
                                    <label for="old_email_modif_email">Ancien Email</label>
                                </div>
                                <div class="form-label-group">
                                    <input type="text" id="email_mofi_email" name="Identifiant_modif_email"
                                        class="form-control" required>
                                    <label for="email_modif_email">Email</label>
                                </div>

                                <div class="form-label-group">
                                    <input type="password" id="mdp_modif_email" name="MotDePasse_modif_email"
                                        class="form-control" required>
                                    <label for="mdp_modif_email">Mot de passe</label>
                                </div>

                                <input type="hidden" name="Type" value="Modif_email" />

                                <button class="btn btn-info" type="submit">VALIDER</button>
                            </form>
                        </div>

                        <div class="col-md-4 text-center">
                            <h4>Modifier Mot de passe</h4>
                            <form id="ModifMDP" class="form-signin" method="post">
                                <div class="form-label-group">
                                    <input type="text" id="email_modif_mdp" name="Identifiant_modif_mdp"
                                        class="form-control" required>
                                    <label for="email_modif_mdp">Email</label>
                                </div>
                                <div class="form-label-group">
                                    <input type="password" id="old_mdp" name="old_MotDePasse_modif_mdp"
                                        class="form-control" required>
                                    <label for="old_mdp_modif_mdp">Ancien Mot de passe</label>
                                </div>
                                <div class="form-label-group">
                                    <input v-validate="'required'" id="pwd_modif_mdp" name="MotDePasse_modif_mdp"
                                        type="password" class="form-control" data-vv-as="votre nouveau mot de passe"
                                        ref="password" required>
                                    <label for="pwd_modif_mdp">Nouveau Mot de Passe</label>
                                </div>

                                <div class="form-label-group">
                                    <input v-validate="'required|confirmed:password'" id="pwd_modif_mdp_confirm"
                                        name="MotDePasse_modif_mdp_confirm" type="password" class="form-control"
                                        data-vv-as="de confirmation" required>
                                    <label for="pwd_modif_mdp_conf">Confirmation</label>
                                </div>

                                <div class="alert alert-danger" v-show="errors.any()">
                                    <div v-if="errors.has('MotDePasse_modif_mdp')">
                                        {{ errors.first('MotDePasse_modif_mdp') }}
                                    </div>
                                    <div v-if="errors.has('MotDePasse_modif_mdp_confirm')">
                                        {{ errors.first('MotDePasse_modif_mdp_confirm') }}
                                    </div>
                                </div>

                                <input type="hidden" name="Type" value="Modif_mdp" />

                                <div class="alert alert-warning" v-show="errors.any()">Complétez pour afficher le
                                    bouton de validation</div>
                                <button class="btn btn-info" type="submit" v-show="!errors.any()">VALIDER</button>
                            </form>
                        </div>

                        <div class="col-md-4 text-center">
                            <h4>Modifier Pseudo</h4>

                            <form id="ModifPseudo" class="form-signin" method="post">
                                <div class="form-label-group">
                                    <input type="text" id="pseudo_modif_pseudo" name="Pseudo_modif_pseudo"
                                        class="form-control" required>
                                    <label for="pseudo_modif_email">Nouveau Pseudo</label>
                                </div>
                                <div class="form-label-group">
                                    <input type="text" id="email_modif_pseudo" name="Identifiant_modif_pseudo"
                                        class="form-control" required>
                                    <label for="email_modif_pseudo">Email</label>
                                </div>

                                <div class="form-label-group">
                                    <input type="password" id="mdp_modif_pseudo" name="MotDePasse_modif_pseudo"
                                        class="form-control" required>
                                    <label for="mdp_modif_pseudo">Mot de passe</label>
                                </div>

                                <input type="hidden" name="Type" value="Modif_pseudo" />

                                <button class="btn btn-info" type="submit">VALIDER</button>
                            </form>
                        </div>
                    </div>
                    <div class="row justify-content-center">
                        <div class="col-md-3 pb-3 pt-2 text-center"><button data-toggle="modal"
                                data-target="#supprimer_compte" class="btn btn-danger" type="submit">SUPPRIMER
                                COMPTE</button></div>
                    </div>
                </div>


                <div class="card mt-2">
                    <div class="card-header bg-info text-light text-center" id="headingOne">
                        <h3>Vos Statistiques</h3>
                    </div>

                    <div id="accordion">
                        <div class="card">
                            <div class="card-header bg-info" id="headingOne">
                                <button class="btn btn-info collapsed" data-toggle="collapse" data-target="#collapseOne"
                                    aria-expanded="false" aria-controls="collapseOne">
                                    <h5>Classement Personnel</h5>
                                </button>
                            </div>

                            <div id="collapseOne" class="collapse" aria-labelledby="headingOne"
                                data-parent="#accordion">
                                <div class="card-body">
                                    <table id="classper" data-sort-name="date" data-sort-order="desc"></table>
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-header bg-info" id="headingTwo">
                                <button class="btn btn-info collapsed" data-toggle="collapse" data-target="#collapseTwo"
                                    aria-expanded="false" aria-controls="collapseTwo">
                                    <h5>Classement Général</h5>
                                </button>
                            </div>
                            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
                                data-parent="#accordion">
                                <div class="card-body">
                                    <table id="classgen" data-sort-name="score" data-sort-order="desc"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </c:if>


            <div class="pb-4 pt-4 text-center"><img src="<c:url value="/img/pacman.png"/>" alt="Personnages" width=500>
            </div>

        </div>
        <div class="modal" id="supprimer_compte">
            <div class="modal-dialog">
                <div class="modal-content">

                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">SUPPRESSION DU COMPTE</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <!-- Modal body -->
                    <form id="suppr_compte" class="form-signin" name="suppr_compte_form" method="post">
                        <div class="modal-body">
                            <label for=typePage>Êtes-vous vraiment sûr de vouloir supprimer votre compte ? </label>

                            <div class="form-label-group">
                                <input type="text" id="email_suppr" name="Identifiant_suppr" class="form-control"
                                    required>
                                <label for="email_suppr">Email</label>
                            </div>
                            <div class="form-label-group">
                                <input type="password" id="mdp_suppr" name="MotDePasse_suppr" class="form-control"
                                    required>
                                <label for="mdp_suppr">Mot de passe</label>
                            </div>

                            <input type="hidden" name="Type" value="Suppr" />

                        </div>

                        <!-- Modal footer -->
                        <div class="modal-footer">
                            <button class="btn btn-outline-info" data-dismiss="modal" type="submit"
                                onclick="this.form.submit()">Valider</button>
                            <button type="button" class="btn btn-outline-danger" data-dismiss="modal">Annuler</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <script>
        initTablePer('#classper', 'http://localhost:8080/Pacman_Score/Partie', 'Adann');
        initTableGen('#classgen', 'http://localhost:8080/Pacman_Score/Partie');
    </script>

    <script type="module" src="<c:url value="/js/vue.js"/>"> </script> <script type="module"
        src="<c:url value="/js/score_data.js"/>"> </script> </body> </html>